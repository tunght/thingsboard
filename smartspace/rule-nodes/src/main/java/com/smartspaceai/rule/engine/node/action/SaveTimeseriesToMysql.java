package com.smartspaceai.rule.engine.node.action;

import lombok.extern.slf4j.Slf4j;
import com.google.common.base.Strings;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thingsboard.rule.engine.api.*;
import org.thingsboard.rule.engine.api.util.TbNodeUtils;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.kv.KvEntry;
import org.thingsboard.server.common.data.kv.TsKvEntry;
import org.thingsboard.server.common.data.plugin.ComponentType;
import org.thingsboard.server.common.msg.TbMsg;
import org.thingsboard.server.common.msg.session.SessionMsgType;
import org.thingsboard.server.common.transport.adaptor.JsonConverter;
import org.thingsboard.server.dao.device.DeviceService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.thingsboard.rule.engine.api.TbRelationTypes.FAILURE;
import static org.thingsboard.rule.engine.api.TbRelationTypes.SUCCESS;

@Slf4j
@RuleNode(type = ComponentType.ACTION, name = "Save People Count to Mysql", configClazz = SaveTimeseriesToMysqlConfiguration.class, nodeDescription = "Save People count data to Mysql", nodeDetails = "This is original for Google DataStudio", uiResources = {
    "static/rulenode/rulenode-core-config.js",
    "static/rulenode/rulenode-core-config.css" }, configDirective = "smartspaceActionNodeTimeseriesMySQLConfig", icon = "file_upload")
public class SaveTimeseriesToMysql implements TbNode {

  private static final ObjectMapper mapper = new ObjectMapper();

  private SaveTimeseriesToMysqlConfiguration config;

  private Connection mysqlConn;

  @Override
  public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
    this.config = TbNodeUtils.convert(configuration, SaveTimeseriesToMysqlConfiguration.class);

    String dbUrl = "jdbc:mysql://" + config.getDatabaseUrl() + "/" + config.getDatabaseName() + "?useSSL=false";
    try {
      mysqlConn = DriverManager.getConnection(dbUrl, config.getDatabaseUsername(), config.getDatabasePassword());
    } catch (SQLException ex) {
      log.error("Can't connect to database (" + dbUrl + ") with user " + config.getDatabaseUsername() + " password "
          + config.getDatabasePassword() + " error: " + ex.getMessage());
      mysqlConn = null;
    }
  }

  @Override
  public void onMsg(TbContext ctx, TbMsg msg) {
    if (!msg.getType().equals(SessionMsgType.POST_TELEMETRY_REQUEST.name())) {
      ctx.tellFailure(msg, new IllegalArgumentException("Unsupported msg type: " + msg.getType()));
      return;
    }

    long ts = -1;
    String tsStr = msg.getMetaData().getValue("ts");
    if (!Strings.isNullOrEmpty(tsStr)) {
      try {
        ts = Long.parseLong(tsStr);
      } catch (NumberFormatException e) {
      }
    } else {
      ts = System.currentTimeMillis();
    }

    String src = msg.getData();
    log.info("Got message " + src);
    // String query = config.getDataQuery();

    try {
      if (mysqlConn == null || mysqlConn.isClosed()) {
        ctx.tellFailure(msg, new Exception("Can't connect to database"));
        return;
      }

      String sql = "INSERT INTO " + this.config.getTableName() + " (time, camera_name, count, in, out) VALUES(?, ?, ?, ?, ?)";
      PreparedStatement pst = mysqlConn.prepareStatement(sql);

      JsonNode jsonNode = mapper.readTree(msg.getData());

      EntityId originator = msg.getOriginator();

      if (originator.getEntityType() == EntityType.DEVICE && (jsonNode.has("count") || jsonNode.has("in") || jsonNode.has("out"))) {
        int count = 0;
        int in = 0;
        int out = 0;
        if (jsonNode.has("count")) {
          count = jsonNode.get("count").asInt();
        }

        if (jsonNode.has("in")) {
          in = jsonNode.get("in").asInt();
        }

        if (jsonNode.has("out")) {
          out = jsonNode.get("out").asInt();
        }
        
        Timestamp sqlTs = new Timestamp(ts);
        String camName = msg.getMetaData().getValue("originatorName");
        log.info("Cam name " + camName);
        pst.setTimestamp(1, sqlTs);
        pst.setString(2, camName);
        pst.setInt(3, count);
        pst.setInt(4, in);
        pst.setInt(5, out);
        pst.executeUpdate();
      }

    } catch (Exception ex) {
      log.info("Error inserting data to mysql " + ex.getMessage());
      ctx.tellFailure(msg, ex);
    }
  }

  @Override
  public void destroy() {
    try {
      if (mysqlConn != null) {
        mysqlConn.close();
      }
    } catch (SQLException ex) {
      log.error("Failed to close mysql connection: " + ex.getMessage());
    }
  }
}
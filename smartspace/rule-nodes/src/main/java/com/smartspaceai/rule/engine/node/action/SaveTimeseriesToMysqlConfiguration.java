package com.smartspaceai.rule.engine.node.action;

import lombok.Data;
import org.thingsboard.rule.engine.api.NodeConfiguration;

@Data
public class SaveTimeseriesToMysqlConfiguration implements NodeConfiguration<SaveTimeseriesToMysqlConfiguration> {

  private String databaseUrl;
  private String databasePassword;
  private String databaseUsername;
  private String databaseName;
  private String dataQuery;
  private String tableName;
  @Override
  public SaveTimeseriesToMysqlConfiguration defaultConfiguration() {
    SaveTimeseriesToMysqlConfiguration configuration = new SaveTimeseriesToMysqlConfiguration();
    configuration.setDatabaseUrl("");
    configuration.setDatabaseUsername("");
    configuration.setDatabasePassword("");
    configuration.setDatabaseName("");
    configuration.setDataQuery("");
    configuration.setTableName("");
    
    return configuration;
  }
}
#!/bin/bash
#
# Copyright © 2016-2019 The Thingsboard Authors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

scp js-executor/target/tb-js-executor.deb tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/js-executor/
scp js-executor/target/Dockerfile tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/js-executor/
scp js-executor/target/start-js-executor.sh tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/js-executor/

scp tb-node/target/thingsboard.deb tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/app-node
scp tb-node/target/Dockerfile tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/app-node/
scp tb-node/target/start-tb-node.sh tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/app-node/

scp web-ui/target/tb-web-ui.deb tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/web-ui/
scp web-ui/target/Dockerfile tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/web-ui/
scp web-ui/target/start-web-ui.sh tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/web-ui/

scp transport/mqtt/target/tb-mqtt-transport.deb tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/mqtt/
scp transport/mqtt/target/Dockerfile tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/mqtt/
scp transport/mqtt/target/start-tb-mqtt-transport.sh tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/mqtt/

scp transport/http/target/tb-http-transport.deb tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/http/
scp transport/http/target/Dockerfile tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/http/
scp transport/http/target/start-tb-http-transport.sh tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/http/

scp transport/coap/target/tb-coap-transport.deb tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/coap/
scp transport/coap/target/Dockerfile tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/coap/
scp transport/coap/target/start-tb-coap-transport.sh tung_sangjen_com@admin.smartspaceai.com:/home/tung_sangjen_com/thingsboard_services/transport/coap/

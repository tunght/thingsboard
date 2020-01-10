
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

folder=$1
docker build -t js-executor $folder/js-executor/target
docker build -t app-node $folder/tb-node/target
docker build -t web-ui $folder/web-ui/target
docker build -t mqtt-transport $folder/transport/mqtt/target
docker build -t http-transport $folder/transport/http/target
docker build -t coap-transport $folder/transport/coap/target



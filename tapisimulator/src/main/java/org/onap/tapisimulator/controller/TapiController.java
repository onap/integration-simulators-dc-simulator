/*
 * ============LICENSE_START=======================================================
 * TAPI-SIMULATOR
 * ================================================================================
 * Copyright (C) 2020 Fujitsu Limited. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.tapisimulator.controller;

import org.onap.tapisimulator.model.SipList;
import org.onap.tapisimulator.service.TapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TapiController {

    @Autowired
    TapiService tapiService;

    @GetMapping("/cxf/tapi/v2/connectivities/service-interface-points/{connection-point}")
    public SipList getTapiServiceInterfacePoint(@PathVariable("connection-point") String cep) {

        return tapiService.getServiceInterfacePoints(cep);

    }

    @PostMapping("/cxf/tapi/v2/connectivities/create-service/{service-name}")
    public String createTapiService(@PathVariable("service-name") String name, @RequestBody String requestbody) {
        tapiService.processService(requestbody);
        return "Service created Successfully";
    }

    @GetMapping("/cxf/tapi/v2/connectivities/get-service/{service-name}")
    public String getTapiService(@PathVariable("service-name") String name) {

        return tapiService.returnService(name);

    }

    @DeleteMapping("/cxf/tapi/v2/connectivities/delete-service/{service-name}")
    public String deleteTapiService(@PathVariable("service-name") String name) {
        tapiService.processDeleteService(name);
        return "Service deleted successfully";

    }

    @GetMapping("/cxf/tapi/v2/connectivities/topology")
    public String getTapiTopology() {

        return tapiService.getTopology();

    }

}

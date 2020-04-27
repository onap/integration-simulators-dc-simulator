/*
 * ============LICENSE_START=======================================================
 * MSA-SIMULATOR
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

package org.onap.msasimulator.controller;

import javax.validation.Valid;

import org.onap.msasimulator.model.MsaServiceCreateRequest;
import org.onap.msasimulator.model.MsaServiceDeleteRequest;
import org.onap.msasimulator.model.MsaServiceResponse;
import org.onap.msasimulator.service.MsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsaController {

    @Autowired
    MsaService msaService;

    @RequestMapping("/")
    public String index() {
        return "Test successful";
    }

    @PostMapping("/cxf/openroadm/openroadm-services/create")
    public ResponseEntity<?> createService(@Valid @RequestBody MsaServiceCreateRequest request) {

        MsaServiceResponse response = msaService.processMsaService(request);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/cxf/openroadm/openroadm-services/delete")
    public ResponseEntity<?> deleteService(@Valid @RequestBody MsaServiceDeleteRequest request) {
        MsaServiceResponse response = msaService.processMsaServiceDelete(request);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/cxf/openroadm/v2/networks/otn-topology")
    public String getMsaTopology() {

        return msaService.getTopology();

    }
}

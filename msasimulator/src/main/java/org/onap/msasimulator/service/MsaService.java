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

package org.onap.msasimulator.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.onap.msasimulator.model.ConfigurationResponseCommon;
import org.onap.msasimulator.model.Link;
import org.onap.msasimulator.model.MsaServiceCreateRequest;
import org.onap.msasimulator.model.MsaServiceDeleteRequest;
import org.onap.msasimulator.model.MsaServiceResponse;
import org.onap.msasimulator.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class MsaService {

    private static Logger log = LoggerFactory.getLogger(MsaService.class);

    @Getter
    private String topology = "";

    private List<Link> linkList = new ArrayList<>();

    private List<String> serviceIdList = new ArrayList<>();

    @Autowired
    Utils utils;

    @PostConstruct
    public void init() throws IOException {
        topology = utils.readFromFile("/opt/onap/msasimulator/templates/msa-topology.json");
        log.debug("Inside init method of MsaService classc {}", topology);
        JSONObject topo = new JSONObject(topology);
        JSONArray topolinks = topo.getJSONArray("ietf-network-topology:link");
        for (Object topolink : topolinks) {
            if (topolink instanceof JSONObject) {
                JSONObject obj = (JSONObject) topolink;
                Link link = new Link(obj.getJSONObject("source").getString("source-node").split("-")[0],
                        obj.getJSONObject("destination").getString("dest-node").split("-")[0]);
                linkList.add(link);

            }

        }
        log.debug("Link list {}", linkList);

    }

    public MsaServiceResponse processMsaService(MsaServiceCreateRequest request) {
        String aend = request.getServiceAEend().getNodeId();
        String zend = request.getServiceZEnd().getNodeId();
        String requestId = request.getSdncRequestHeader().getRequestId();
        Link link = new Link(aend, zend);
        if (linkList.contains(link)) {
            serviceIdList.add(requestId);
            return formServiceResponse(requestId, "200", "Service creation was successful");
        } else {
            return formServiceResponse(requestId, "410", "Link not available , Service creation failed");

        }

    }

    private MsaServiceResponse formServiceResponse(String requestId, String responseCode, String responseMessage) {
        MsaServiceResponse response = new MsaServiceResponse();
        ConfigurationResponseCommon configuration = new ConfigurationResponseCommon();
        configuration.setRequestId(requestId);
        configuration.setResponseCode(responseCode);
        configuration.setResponseMessage(responseMessage);
        configuration.setAckFinalIndicator("Y");
        response.setConfigurationResponseCommon(configuration);
        return response;

    }

    public MsaServiceResponse processMsaServiceDelete(MsaServiceDeleteRequest request) {
        String requestId = request.getSdncRequestHeader().getRequestId();
        if (serviceIdList.contains(requestId)) {
            serviceIdList.remove(requestId);
            return formServiceResponse(requestId, "200", "Service deletion success");
        } else {
            return formServiceResponse(requestId, "410", "Service does not exist");
        }

    }

}

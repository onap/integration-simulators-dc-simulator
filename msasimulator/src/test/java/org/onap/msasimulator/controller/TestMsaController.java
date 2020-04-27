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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.onap.msasimulator.Application;
import org.onap.msasimulator.Config;
import org.onap.msasimulator.TestUtils;
import org.onap.msasimulator.model.MsaServiceCreateRequest;
import org.onap.msasimulator.model.MsaServiceResponse;
import org.onap.msasimulator.service.MsaService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = "spring.main.allow-bean-definition-overriding=true",
        classes = {Application.class, Config.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMsaController {

    @InjectMocks
    MsaController msaController;

    @Mock
    MsaService msaService;

    private MockMvc mvc;

    private ObjectMapper mapper;

    @Before
    public void beforeEach() {

        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.standaloneSetup(msaController).build();
        mapper = new ObjectMapper();

    }

    @Test
    public void testTopology() throws Exception {
        String topology = TestUtils.readFileclasspath("topology.json");
        String uri = "/cxf/openroadm/v2/networks/otn-topology";
        when(msaService.getTopology()).thenReturn(topology);
        MvcResult mvcResult = mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, topology);
    }

    @Test
    public void testServiceCreateEndpoint() throws Exception {
        String serviceCreaterequest = TestUtils.readFileclasspath("service-create-success.json");
        MsaServiceCreateRequest request = mapper.readValue(serviceCreaterequest, MsaServiceCreateRequest.class);
        String response = "{\n" + "   \"configuration-response-common\":{\n" + "      \"request-id\":\"123456\",\n"
                + "      \"response-code\":\"200\",\n"
                + "      \"response-message\":\"Service creation was successful\",\n"
                + "      \"ack-final-indicator\":\"Y\"\n" + "   }\n" + "}";
        MsaServiceResponse expectedResponse = mapper.readValue(response, MsaServiceResponse.class);
        String uri = "/cxf/openroadm/openroadm-services/create";
        when(msaService.processMsaService(request)).thenReturn(expectedResponse);
        MvcResult mvcResult = mvc.perform(post(uri).content(serviceCreaterequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        MsaServiceResponse actualResponse = mapper.readValue(content, MsaServiceResponse.class);
        assertEquals(actualResponse, expectedResponse);
    }

}

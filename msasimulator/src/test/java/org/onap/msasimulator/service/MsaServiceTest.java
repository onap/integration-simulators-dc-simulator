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

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.onap.msasimulator.Application;
import org.onap.msasimulator.Config;
import org.onap.msasimulator.TestUtils;
import org.onap.msasimulator.model.MsaServiceCreateRequest;
import org.onap.msasimulator.model.MsaServiceDeleteRequest;
import org.onap.msasimulator.model.MsaServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = "spring.main.allow-bean-definition-overriding=true",
        classes = {Application.class, Config.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MsaServiceTest {

    @Autowired
    MsaService msaService;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetTopology() {
        String topology = TestUtils.readFileclasspath("topology.json");
        assertEquals(topology, msaService.getTopology());
    }

    @Test
    public void testServiceCreateFail() throws JsonParseException, JsonMappingException, IOException {
        String requestJson = TestUtils.readFileclasspath("service-create-failure.json");
        MsaServiceCreateRequest request = objectMapper.readValue(requestJson, MsaServiceCreateRequest.class);
        String response = "{\n" + "   \"configuration-response-common\":{\n" + "      \"request-id\":\"123456\",\n"
                + "      \"response-code\":\"410\",\n"
                + "      \"response-message\":\"Link not available , Service creation failed\",\n"
                + "      \"ack-final-indicator\":\"Y\"\n" + "   }\n" + "}";
        MsaServiceResponse expectedResponse = objectMapper.readValue(response, MsaServiceResponse.class);

        MsaServiceResponse actualResponse = msaService.processMsaService(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testServiceCreatSuccessl() throws JsonParseException, JsonMappingException, IOException {
        String requestJson = TestUtils.readFileclasspath("service-create-success.json");
        MsaServiceCreateRequest request = objectMapper.readValue(requestJson, MsaServiceCreateRequest.class);
        String response = "{\n" + "   \"configuration-response-common\":{\n" + "      \"request-id\":\"123456\",\n"
                + "      \"response-code\":\"200\",\n"
                + "      \"response-message\":\"Service creation was successful\",\n"
                + "      \"ack-final-indicator\":\"Y\"\n" + "   }\n" + "}";
        MsaServiceResponse expectedResponse = objectMapper.readValue(response, MsaServiceResponse.class);

        MsaServiceResponse actualResponse = msaService.processMsaService(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testServiceDelete() throws JsonParseException, JsonMappingException, IOException {
        String requestJson = TestUtils.readFileclasspath("service-delete-request.json");
        MsaServiceDeleteRequest request = objectMapper.readValue(requestJson, MsaServiceDeleteRequest.class);
        String response = "{\n" + "   \"configuration-response-common\":{\n" + "      \"request-id\":\"123456\",\n"
                + "      \"response-code\":\"200\",\n" + "      \"response-message\":\"Service deletion success\",\n"
                + "      \"ack-final-indicator\":\"Y\"\n" + "   }\n" + "}";
        MsaServiceResponse expectedResponse = objectMapper.readValue(response, MsaServiceResponse.class);

        MsaServiceResponse actualResponse = msaService.processMsaServiceDelete(request);
        assertEquals(expectedResponse, actualResponse);
    }

}

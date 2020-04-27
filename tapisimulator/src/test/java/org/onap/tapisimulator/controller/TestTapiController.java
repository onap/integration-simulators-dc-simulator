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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.onap.tapisimulator.Application;
import org.onap.tapisimulator.Config;
import org.onap.tapisimulator.TestUtils;
import org.onap.tapisimulator.service.TapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = "spring.main.allow-bean-definition-overriding=true",
        classes = {Application.class, Config.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTapiController {
    @LocalServerPort
    private int port;

    @Autowired
    TapiService tapiService;

    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void beforeEach() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTopology() throws Exception {
        String topology = TestUtils.readFileclasspath("tapi-topology.json");
        String uri = "http://localhost:" + port + "/cxf/tapi/v2/connectivities/topology";
        ResponseEntity<String> response = this.restTemplate.getForEntity(uri, String.class);

        HttpStatus status = response.getStatusCode();
        assertEquals(status, HttpStatus.OK);
        String content = response.getBody();
        assertEquals(content, topology);
    }

    @Test
    public void testServiceCreateTapi() throws Exception {
        String serviceCreaterequest = TestUtils.readFileclasspath("service-create-tapi.json");
        String expectedResponse = "Service created Successfully";
        String name = "test";
        String uri = "http://localhost:" + port + "/cxf/tapi/v2/connectivities/create-service/" + name;
        ResponseEntity<String> response = this.restTemplate.postForEntity(uri, serviceCreaterequest, String.class);
        HttpStatus status = response.getStatusCode();
        assertEquals(HttpStatus.OK, status);
        String content = response.getBody();
        assertEquals(content, expectedResponse);
    }

    @Test
    public void deleteTapiServiceTest() {
        String expectedResponse = "Service deleted successfully";
        String name = "test";
        String uri = "http://localhost:" + port + "/cxf/tapi/v2/connectivities/delete-service/" + name;
        HttpEntity<String> entity = new HttpEntity<>("");
        ResponseEntity<String> response = this.restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
        HttpStatus status = response.getStatusCode();
        assertEquals(HttpStatus.OK, status);
        String content = response.getBody();
        assertEquals(content, expectedResponse);

    }

}

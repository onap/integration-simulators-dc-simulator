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

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.onap.msasimulator.model.Link;
import org.onap.msasimulator.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class MsaService {

	private static Logger log = LoggerFactory.getLogger(MsaService.class);

	@Getter
	private String topology = "";

	private  List<Link> linkList = new ArrayList<>();

	private List<String> serviceIdList = new ArrayList<>();

	@PostConstruct
	public void init() throws IOException {
		topology = Utils.readFromFile("/opt/onap/msasimulator/templates/msa-topology.json");
		log.debug("Inside init method of MsaService class");
		Link link1 = new Link("OWBSPDRNE15", "OWBSPDRNE16");
		Link link2 = new Link("OWBSPDRNE11", "OWBSPDRNE12");
		Link link3 = new Link("OWBSPDRNE13", "OWBSPDRNE14");
		linkList.add(link1);
		linkList.add(link2);
		linkList.add(link3);
	}

	public String processMsaService(String requestBody) {
		JSONObject request = new JSONObject(requestBody);
		String aend = request.getJSONObject("service-a-end").getString("node-id");
		String zend = request.getJSONObject("service-z-end").getString("node-id");
		String requestId = request.getJSONObject("sdnc-request-header").getString("request-id");
		Link link = new Link(aend, zend);
		if (linkList.contains(link)) {
			serviceIdList.add(requestId);
			return formServiceResponse(requestId, "200", "Service creation was successful");
		} else {
			return formServiceResponse(requestId, "410", "Link not available , Service creation failed");

		}

	}

	private String formServiceResponse(String requestId, String responseCode, String responseMessage) {
		JSONObject response = new JSONObject();
		JSONObject configuration = new JSONObject();
		configuration.put("request-id", requestId);
		configuration.put("ack-final-indicator", "Y");
		configuration.put("response-code", responseCode);
		configuration.put("response-message", responseMessage);
		response.put("configuration-response-common", configuration);
		return response.toString();

	}

	public String processMsaServiceDelete(String requestBody) {
		JSONObject request = new JSONObject(requestBody);
		String requestId = request.getJSONObject("sdnc-request-header").getString("request-id");
		if (serviceIdList.contains(requestId)) {
			serviceIdList.remove(requestId);
			return formServiceResponse(requestId, "200", "Service deletion success");
		} else {
			return formServiceResponse(requestId, "410", "Service does not exist");
		}

	}

}

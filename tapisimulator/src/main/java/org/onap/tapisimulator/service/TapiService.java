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
package org.onap.tapisimulator.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.onap.tapisimulator.model.Sip;
import org.onap.tapisimulator.model.SipList;
import org.onap.tapisimulator.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TapiService {

	private static Logger log = LoggerFactory.getLogger(TapiService.class);

	private volatile Map<String, String> serviceMap = new HashMap<>();

	@Autowired
	ObjectMapper mapper;

	private SipList siplist = null;

	private List<Sip> sip = null;
 
  private String topology;

	@PostConstruct
	public void init() throws IOException {
		String domainController = System.getenv("Controller");
		String servicePoints = "";
		if (domainController.equals("TAPI1")) {
			servicePoints = Utils.readFromFile("/opt/onap/tapisimulator/templates/tapi1-siplist.json");
			topology = Utils.readFromFile("/opt/onap/tapisimulator/templates/tapi1-topology.json");
			siplist = mapper.readValue(servicePoints, SipList.class);
			sip = siplist.getSip();
			log.debug("Inside init method of TapiService class");
		} else if (domainController.equals("TAPI2")) {
			servicePoints = Utils.readFromFile("/opt/onap/tapisimulator/templates/tapi2-siplist.json");
			topology = Utils.readFromFile("/opt/onap/tapisimulator/templates/tapi2-topology.json");
			siplist = mapper.readValue(servicePoints, SipList.class);
			sip = siplist.getSip();
			log.debug("Inside init method of TapiService class");
		}

	}

	public SipList getServiceInterfacePoints(String connectionPoint) {

		List<Sip> curSip = sip.stream().filter(c -> c.getUuid().equals(connectionPoint)).collect(Collectors.toList());

		SipList newSip = new SipList();
		newSip.setSip(curSip);
		return newSip;
	}

	public Map<String, String> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(String key, String value) {
		serviceMap.put(key, value);
	}

	public void processService(String request) {
		JSONObject json = new JSONObject(request);
		JSONArray conectivityList = json.getJSONArray("create-connectivity-service-input-list");
		JSONObject connec = conectivityList.getJSONObject(0);
		JSONArray eplist = connec.getJSONArray("end-point");
		JSONArray name = connec.getJSONArray("name");
		String serviceName = name.getJSONObject(0).getString("value");

		createSErviceMap(eplist, serviceName, name);

	}

	public String returnService(String name) {

		return serviceMap.get(name);

	}

	public String processDeleteService(String name) {

		return serviceMap.remove(name);

	}

	public String getTopology() {

		return topology;

	}

	private void createSErviceMap(JSONArray eplist, String serviceName, JSONArray name) {
		JSONObject serviceEntry = new JSONObject();
		serviceEntry.put("end-point", eplist);
		serviceEntry.put("topology-constraint", new JSONArray());
		serviceEntry.put("routing-constraint", new JSONObject());
		serviceEntry.put("connectivity-constraint", new JSONObject());
		serviceEntry.put("uuid", UUID.randomUUID().toString());
		serviceEntry.put("layer-protocol-name", "ODU");
		serviceEntry.put("direction", "BIDIRECTIONAL");
		serviceEntry.put("administrative-state", "UNLOCKED");
		serviceEntry.put("operational-state", "ENABLED");
		JSONArray connection = new JSONArray();
		JSONObject connectionObject = new JSONObject();
		connectionObject.put("connection-uuid", UUID.randomUUID().toString());
		connection.put(0, connectionObject);
		serviceEntry.put("connection", connection);
		serviceEntry.put("name", name);

		JSONObject service = new JSONObject();
		JSONArray serviceList = new JSONArray();
		serviceList.put(0, serviceEntry);
		service.put("service", serviceList);

		serviceMap.put(serviceName, service.toString());
	}

}

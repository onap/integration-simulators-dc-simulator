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

package org.onap.msasimulator.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEndPoint {
    @NotNull
    @JsonProperty(value = "rx-direction")
    private DirectionDetails rxDirection;

    @NotNull
    @JsonProperty(value = "service-format")
    private String serviceFormat;

    @NotNull
    @JsonProperty(value = "node-id")
    private String nodeId;

    @NotNull
    @JsonProperty(value = "mapping-mode")
    private String mappingMode;

    @NotNull
    @JsonProperty(value = "optic-type")
    private String opticType;

    @NotNull
    @JsonProperty(value = "clli")
    private String clli;

    @NotNull
    @JsonProperty(value = "service-rate")
    private String serviceRate;

    @NotNull
    @JsonProperty(value = "tx-direction")
    private DirectionDetails txDirection;

    @NotNull
    @JsonProperty(value = "ethernet-encoding")
    private String ethernetEncoding;

}

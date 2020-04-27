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

package org.onap.tapisimulator;

import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.onap.tapisimulator.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Utils utils() {
        Utils utils = Mockito.mock(Utils.class);
        String topology = TestUtils.readFileclasspath("tapi-topology.json");
        String sipList = TestUtils.readFileclasspath("siplist.json");
        when(utils.readFromFile("/opt/onap/tapisimulator/templates/tapi1-topology.json")).thenReturn(topology);
        when(utils.readFromFile("/opt/onap/tapisimulator/templates/tapi1-siplist.json")).thenReturn(sipList);

        return utils;
    }

}

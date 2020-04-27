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

package org.onap.msasimulator;

import org.onap.msasimulator.utils.Utils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.context.annotation.Bean;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class Config {

    @Bean
    public Utils utils() {
        Utils utils = Mockito.mock(Utils.class);
        String topology = readFileclasspath("topology.json");
        when(utils.readFromFile("/opt/onap/msasimulator/templates/msa-topology.json")).thenReturn(topology);

        return utils;
    }

    public static String readFileclasspath(String filename) {
        String data = "";
        Resource resource = new ClassPathResource(filename);
        InputStream inputStream;
        try {
            inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            data = new String(bdata, StandardCharsets.UTF_8);
            return data;
        } catch (IOException e) {
            return data;
        }
    }

}

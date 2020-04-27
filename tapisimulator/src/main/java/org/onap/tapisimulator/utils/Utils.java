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

package org.onap.tapisimulator.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class Utils {

    public void readFileclasspath() throws IOException {
        File file = ResourceUtils.getFile("classpath:config/sample.txt");

        // File is found
        System.out.println("File Found : " + file.exists());

        // Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);
    }

    public String readFromFile(String file) {
        String content = "";
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            content = bufferedReader.readLine();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                content = content.concat(temp);
            }
            content = content.trim();
            bufferedReader.close();
        } catch (Exception e) {
            content = null;
        }
        return content;
    }

}

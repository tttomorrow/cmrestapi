/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 *
 * CM is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 *          http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package org.opengauss.cmrestapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/**
 * @Title: CMRestAPIClient
 * @author: xuemengen
 * @Description:
 * Client for pushing newest master info to server in application or other places.
 * Created on: 2022/09/07
 */
public class CMRestAPIClient {
    private String url;
    private WebClient webClient;
    private Logger logger = LoggerFactory.getLogger(CMRestAPIClient.class);
    
    public CMRestAPIClient(String uri) {
        this.url = uri;
        this.webClient = WebClient.create(uri);
    }
    
    /**
     * @Title: pushMasterInfo
     * @Description:
     * Push newest master info (ip and port) to url.
     * @param masterIpPort
     * void
     */
    public void pushMasterInfo(String masterIpPort) {
        logger.info("Sendind newest master info({}) to {}", masterIpPort, url);
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("MasterIpPort", masterIpPort);

        try {
            String response = webClient.put()
                    .uri("")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromFormData(bodyValues))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            logger.info("Receive response {} from server.", response);
        } catch (WebClientRequestException e) {
            logger.error("The server {} maybe offline.\nDetail:", url, e);
        }
    }
    
    /**
     * @Title: pushStandbysInfo
     * @Description:
     * Push current standbys' info(ip:port) to url.
     * void
     */
    public void pushStandbysInfo() {
        String standbysInfo = CMRestAPI.peerIpPorts;
        logger.info("Sendind newest standbys info({}) to {}", standbysInfo, url);
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("StanbysInfo", standbysInfo);

        try {
            String response = webClient.put()
                    .uri("")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromFormData(bodyValues))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            logger.info("Receive response {} from server.", response);
        } catch (WebClientRequestException e) {
            logger.error("The server {} maybe offline.\nDetail:", url, e.getMessage());
        }
    }
}

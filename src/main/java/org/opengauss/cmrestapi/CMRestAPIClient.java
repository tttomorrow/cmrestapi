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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * @Title: CMRestAPIClient
 * @author: xuemengen
 * @Description:
 * Client for pushing newest master info to server in application or other places.
 * Created on: 2022/09/07
 */
public class CMRestAPIClient {
    private String url;
    private RestTemplate restTemplate = null;
    private Logger logger = LoggerFactory.getLogger(CMRestAPIClient.class);

    public CMRestAPIClient(String url) {
        this.url = url;
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        // set connect timeout = 1s
        clientHttpRequestFactory.setConnectTimeout(1000);
        // set sending timeout = 1s
        clientHttpRequestFactory.setReadTimeout(1000);
        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
    }

    /**
     * @Title: pushMasterInfo
     * @Description:
     * Push newest master info (ip and port) to url.
     * @param masterIpPort
     * void
     */
    public void pushMasterInfo(String masterInfo) {
        logger.info("Sendind newest master info({}) to {}", masterInfo, url);
        try {
            HttpEntity<String> entity = new HttpEntity<>(masterInfo);
            ResponseEntity<String> response = restTemplate.exchange(url + "/MasterInfo", HttpMethod.PUT, entity, String.class);
            logger.info("StatusCode: {}", response.getStatusCode());
            logger.info("Msg: {}", response.getBody());
        } catch (ResourceAccessException | HttpClientErrorException e) {
            logger.error("Failed to send newest master info.\nDetail:{}", url, e.getMessage());
        }
        logger.info("Send newest master info successfully.");
    }

    /**
     * @Title: pushStandbysInfo
     * @Description:
     * Push current standbys' info(ip:port) to url.
     * void
     */
    public void pushStandbysInfo(String standbyInfo) {
        logger.info("Sendind newest standby info({}) to {}", standbyInfo, url);
        try {
            HttpEntity<String> entity = new HttpEntity<>(standbyInfo);
            ResponseEntity<String> response = restTemplate.exchange(url + "/StandbyInfo", HttpMethod.PUT, entity, String.class);
            logger.info("Response status code: {}", response.getStatusCode());
            logger.info("Response msg: {}", response.getBody());
        } catch (ResourceAccessException | HttpClientErrorException e) {
            logger.error("Failed to send newest standby info.\nDetail:{}", url, e.getMessage());
        }
        logger.info("Send newest standby info successfully.");
    }
}

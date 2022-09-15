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
package com.application.applicationdemo;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: ApplicationServer
 * @author: xuemengen
 * @Description: Listen and receive info push request.
 * Created on: 2022/09/14
 */
@RestController
public class ApplicationServer {
    /**
     * @Title: receiveMasterAndStandbyInfo
     * @Description:
     * Receive master and standby info.
     * @param masterIpPort
     * @param stanbysInfo
     * @return
     * boolean
     */
    @PutMapping("/CMRestAPI")
    public boolean receiveMasterAndStandbyInfo(
            @RequestParam(value = "MasterIpPort", required = false, defaultValue = "")String masterIpPort,
            @RequestParam(value = "StanbysInfo", required = false, defaultValue = "")String stanbysInfo) {
        if (masterIpPort != null && !"".equals(masterIpPort)) {
            // handle master info
            System.out.println("Received put master info request, current master info is " + masterIpPort);
        }
        if (stanbysInfo != null && !"".equals(stanbysInfo)) {
            // handle standbys info
            System.out.println("Received put standbys info request, current standbys info is " + stanbysInfo);
        }
        return true;
    }
}
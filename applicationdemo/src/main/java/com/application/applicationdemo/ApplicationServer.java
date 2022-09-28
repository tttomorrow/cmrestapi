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
import org.springframework.web.bind.annotation.RequestBody;
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
    @PutMapping("/CMRestAPI/MasterInfo")
    public String receiveMasterInfo(@RequestBody String masterInfo) {
        String result = "Recvieve master info is null.";
        if (masterInfo != null && !"".equals(masterInfo)) {
            // handle master info
            System.out.println("Received put master info request, current master info is " + masterInfo);
            result = "Recieved master info successfully.";
        }
        return result;
    }

    @PutMapping("/CMRestAPI/StandbyInfo")
    public String receiveStandbyInfo(@RequestBody String stanbyInfo) {
        String result = "Recvieve standby info is null.";
        if (stanbyInfo != null && !"".equals(stanbyInfo)) {
            // handle standby info
            System.out.println("Received put standby info request, current standbys info is " + stanbyInfo);
            result = "Recieved standby info successfully.";
        }
        return result;
    }
}
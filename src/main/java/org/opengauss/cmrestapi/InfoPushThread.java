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

/**
 * @Title: InfoPushThread
 * @author: xuemengen
 * @Description: InfoPushThread
 * Created on: 2022/09/08
 */
public class InfoPushThread implements Runnable {
    private Thread thread;
    private final String THREAD_NAME;
    private String recvAddrUrl;
    private String masterIpPort;
    private Logger logger = LoggerFactory.getLogger(InfoPushThread.class);
    
    InfoPushThread(int threadNo, String recvAddrUrl, String masterIpPort) {
        this.THREAD_NAME = "InfoPushThread-" + threadNo;
        this.masterIpPort = masterIpPort;
        this.recvAddrUrl = recvAddrUrl;
    }

    @Override
    public void run() {
        CMRestAPIClient client = new CMRestAPIClient(recvAddrUrl);
        client.pushMasterInfo(masterIpPort);
        client.pushStandbysInfo();
    }

    /**
     * @Title: start
     * @Description:
     * Start entry of infoPushThread.
     * void
     */
    public void start () {
        logger.info("Starting thread {}, recvAddrUrl={}, masterIpPort={}",
                THREAD_NAME, recvAddrUrl, masterIpPort);
        if (thread == null) {
            thread = new Thread(this, THREAD_NAME);
            thread.start();
        }
    }
}

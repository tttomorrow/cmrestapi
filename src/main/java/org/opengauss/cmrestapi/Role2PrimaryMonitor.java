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

import java.util.Map;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opengauss.cmrestapi.OGCmdExecuter.CmdResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: Role2PrimaryMonitor
 * @author: xuemengen
 * @Description:
 * Monitor of data instance role change to primary.
 * Created on: 2022/09/08
 */
public class Role2PrimaryMonitor implements Runnable {
    private Thread thread;
    private final String THREAD_NAME;
    private OGCmdExecuter ogCmdExecuter;
    private String currentLocalRole;
    private String masterIpPort;
    private Logger logger = LoggerFactory.getLogger(Role2PrimaryMonitor.class);
    
    public Role2PrimaryMonitor() {
        THREAD_NAME = "RoleChangeToPrimaryMonitor";
        currentLocalRole = "";
        ogCmdExecuter = new OGCmdExecuter(CMRestAPI.envFile);
        masterIpPort = CMRestAPI.hostIp + ":" + CMRestAPI.port;
    }

    /**
     * @Title: Role2Primary
     * @Description:
     * Check whether dn role change to primary.
     * @return
     * boolean
     */
    private boolean roleChanged2Primary() {
        CmdResult cmdResult = ogCmdExecuter.gsctlQuery();
        if (cmdResult.statusCode != 0) {
            logger.error("Exec gs_ctl query cmd failed!");
            return false;
        }
        String localRole = null;
        Pattern pattern = Pattern.compile(".+local_role.+: (.+)\\s+");
        Matcher matcher = pattern.matcher(cmdResult.resultString);
        if (matcher.find()) {
            localRole = matcher.group(1);
        }
        if (!currentLocalRole.equals(localRole)) {
            // update currentLocalRole
            currentLocalRole = localRole;
            if ("Primary".equals(localRole)) {
                logger.info("Role change to primary happened, current node becomes to primary! "
                        + "Current master ip:port={}.", masterIpPort);
                return true;
            } else {
                logger.info("Current local role change to {}.", localRole);
            }
        }
        return false;
    }
    
    /**
     * @Title: run
     * @Description:
     * Main entry of monitor.
     * @return
     * void
     */
    @Override
    public void run() {
        try {
            for (;;) {
                Thread.sleep(1000);
                boolean hasRoleChanged2Priamry = roleChanged2Primary();
                if (!hasRoleChanged2Priamry) {
                    continue;
                }
                Map<String, String> ipPortRecvAddrs = ogCmdExecuter.getRecvAddrList();
                Collection<String> recvAddrList = ipPortRecvAddrs.values();
                int i = 0;
                for (String url : recvAddrList) {
                    ++i;
                    // start a new PushInfoThread to send masterIpPort to every recvAddr
                    new InfoPushThread(i, url, masterIpPort).start();
                }
            }
        } catch (InterruptedException e) {
            logger.error("{}", e);
        }
    }
    
    /**    
     * @Title: start
     * @Description:
     * Start this thread.
     * void
     */
    public void start() {
        logger.info("Starting thread {}.", THREAD_NAME);
        if (thread == null) {
            thread = new Thread(this, THREAD_NAME);
            thread.start ();
        }
    }
}

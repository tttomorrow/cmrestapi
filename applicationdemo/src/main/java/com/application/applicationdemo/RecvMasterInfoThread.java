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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: RecvMasterInfoThread
 * @author: xuemengen
 * @Description: RecvMasterInfoThread
 * Created on: 2022/09/14
 */
@SpringBootApplication
public class RecvMasterInfoThread implements Runnable {
	private Thread t;
	private String threadName;
	
	public RecvMasterInfoThread() {
		threadName = "RecvMasterInfoThread";
	}
	
	void start() {
		System.out.println("Starting " +  threadName );
    	if (t == null) {
    		t = new Thread (this, threadName);
    		t.start ();
		}
	}

	@Override
	public void run() {
		SpringApplication.run(RecvMasterInfoThread.class);
	}

}

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

/**
 * @Title: ErrorCode
 * @author: xuemengen
 * @Description:
 * Define error CODE according to linux error CODE.
 * Created on: 2022/09/08
 */
public enum ErrorCode {
    ENOENT(2, "No such file or directory"), 
    ESRCH(3, "No such process"),
    ENOTDIR(20, "Not a directory"),
    EISDIR(21, "Is a directory"),
    EINVAL(22, "Invalid argument"),
    EHOSTDOWN(112, "Host is down"),
    EUNKNOWN(255, "Unknown error");

    private final int CODE;
    private final String DESCRIPTION;

    ErrorCode(int value, String description) {
        this.CODE = value;
        this.DESCRIPTION = description;
    }

    /**
     * @Title: GetCode
     * @Description:
     * Get CODE.
     * @return
     * int: error CODE
     */
    public int getCode() {
        return CODE;
    }
    
    /**
     * @Title: GetDescription
     * @Description:
     * Get DESCRIPTION
     * @return
     * String: error CODE DESCRIPTION
     */
    public String getDescription() {
        return DESCRIPTION;
    }
}
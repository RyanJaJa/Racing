package com.exce.model.parameter;

import java.io.Serializable;

/*
* 追號狀態
*/
public enum ChaseStatus implements Serializable {
    ACTIVE("進行中"),
    DONE("已完成"),
    DONEBYSTOPCHASE("已完成(中獎後停追)"),
    CLIENTCANCEL("已撤單"),
    // 後台人員可操作CANCEL、SUSPEND
    CANCEL("已取消"),
    SUSPEND("已暫停"),;

    private final String description;

    ChaseStatus(String description) {
        this.description = description;
    }
}
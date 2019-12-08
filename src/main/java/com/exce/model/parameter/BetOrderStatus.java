package com.exce.model.parameter;

import java.io.Serializable;

/*
* 訂單狀態
*/
public enum BetOrderStatus implements Serializable {

    NONE_OPEN("未開獎"),
    LOSE("未中獎"),
    WIN("中獎"),
    CLOSE("已封盤"),
    CLIENT_CANCEL("已撤單"),
    // 後台人員可操作CANCEL、SUSPEND
    CANCEL("已取消"),
    SUSPEND("已暫停"),;

    private final String description;

    BetOrderStatus(String description) {
        this.description = description;
    }
}
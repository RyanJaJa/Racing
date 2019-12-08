package com.exce.exception;

import java.io.Serializable;

public class GoldLuckException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1308166459508211789L;
    private SystemErrorCode errorCode;

    public GoldLuckException(SystemErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemErrorCode getErrorCode() {
        return errorCode;
    }
}

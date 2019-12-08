package com.exce.exception;

import java.io.Serializable;

public class PaymentException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1;

    public PaymentException(String message) {
        super(message);
    }
}

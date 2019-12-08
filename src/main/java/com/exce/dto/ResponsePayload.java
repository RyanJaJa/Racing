package com.exce.dto;

import com.exce.exception.SystemErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponsePayload<T> extends ResponseEntity<T> {

    private SystemErrorCode systemCode;
    private String description;

    public ResponsePayload(HttpStatus status) {
        super(status);
    }

    public ResponsePayload(T body, HttpStatus status) {
        super(body, status);
    }

    public ResponsePayload(HttpStatus status, SystemErrorCode systemCode, String description) {
        super(status);
        this.systemCode = systemCode;
        this.description = description;
    }

    public ResponsePayload(T body, HttpStatus status, SystemErrorCode systemCode, String description) {
        super(body, status);
        this.systemCode = systemCode;
        this.description = description;
    }

    public ResponsePayload(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponsePayload(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public SystemErrorCode getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(SystemErrorCode systemCode) {
        this.systemCode = systemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

/*
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponsePayload [data=");
        if (getData() != null) {
            builder.append(getData().toString());
        } else {
            builder.append(getData());
        }
        builder.append(", errorCode=");
        builder.append(getErrorCode());
        builder.append(", errorDescription=");
        builder.append(getErrorDescription());
        builder.append("]");
        return builder.toString();
    }
*/
}

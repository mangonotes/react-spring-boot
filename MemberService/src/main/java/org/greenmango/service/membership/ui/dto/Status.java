package org.greenmango.service.membership.ui.dto;

import org.greenmango.service.membership.constants.StatusCode;

import java.io.Serializable;

public final class Status implements Serializable {

    private final int code;
    private final int errorCode;
    private final String errorMessage;

    public Status(int code, int errorCode, String errorMessage) {
        this.code = code;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public Status(){
        this(StatusCode.SUCCESS.getCode(),0,null);
    }

    public int getCode() {
        return code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

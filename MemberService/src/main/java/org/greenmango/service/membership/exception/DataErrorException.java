package org.greenmango.service.membership.exception;

import org.greenmango.service.membership.constants.ErrorCode;

public class DataErrorException extends RuntimeException {
    private ErrorCode errorCode;
    private static final long serialVersionUID = 1L;

    public DataErrorException() {
        super();
    }

    public DataErrorException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DataErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataErrorException(String message) {
        super(message);
    }

    public DataErrorException(Throwable cause) {
        super(cause);
    }

    public DataErrorException(ErrorCode errorCode) {
        super(errorCode.getErrorDescription());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

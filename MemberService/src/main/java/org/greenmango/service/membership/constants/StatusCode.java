package org.greenmango.service.membership.constants;

/**
 * Status code for http response
 */
public enum StatusCode {
    SUCCESS(200),
    ERROR(400),
    OTHERS(500);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

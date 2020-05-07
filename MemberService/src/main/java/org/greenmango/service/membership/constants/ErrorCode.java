package org.greenmango.service.membership.constants;

/**
 * Error code enum
 * */
public enum ErrorCode {
    SUCCESS(220, "Successfully created the request."),
    ELEMENT_NOT_FIND(223, "Elements not find."),
    SUCCESS_DELETE(230, "Successfully deleted the request."),
    FORMAT_EXCEPTION(501, "Request not able to format."),
    SYSTEM_ERROR(400, "System error , please contact system admin."),
    UPDATE_ERROR(224, "Member not able to update, please contact system admin.");

    private final int errorCode;
    private final String errorDescription;
    ErrorCode(int errorCode, String errorDescription)
    {
        this.errorCode= errorCode;
        this.errorDescription=errorDescription;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public String getErrorDescription() {
        return errorDescription;
    }
}

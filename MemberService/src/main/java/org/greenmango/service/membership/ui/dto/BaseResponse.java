package org.greenmango.service.membership.ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private final T response;
    private final Status status;
    @JsonCreator
    public BaseResponse(T response, Status status){
        this.response=response;
        this.status=status;
    }
    public T getResponse() {
        return response;
    }

    public Status getStatus() {
        return status;
    }
}

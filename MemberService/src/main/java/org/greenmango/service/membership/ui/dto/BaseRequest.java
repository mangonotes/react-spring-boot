package org.greenmango.service.membership.ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class BaseRequest<T>  implements Serializable {
    private final T request;
    @JsonCreator
    public BaseRequest(T request){
        this.request=request;
    }
    public T getRequest() {
        return request;
    }
}

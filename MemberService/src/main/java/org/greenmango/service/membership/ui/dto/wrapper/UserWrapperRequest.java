package org.greenmango.service.membership.ui.dto.wrapper;

public class  UserWrapperRequest<T> {
    private final String user;
    private final T request;

    public UserWrapperRequest(String user, T request) {
        this.user = user;
        this.request = request;
    }

    public String getUser() {
        return user;
    }

    public T getRequest() {
        return request;
    }
}

package org.greenmango.service.membership.ui.dto.wrapper;

public class HostWrapperResponse<T> {
    private final String host;
    private final T response;

    public HostWrapperResponse(String host, T response) {
        this.host = host;
        this.response = response;
    }

    public String getHost() {
        return host;
    }

    public T getResponse() {
        return response;
    }
}

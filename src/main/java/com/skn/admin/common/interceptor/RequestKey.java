package com.skn.admin.common.interceptor;

import java.util.Objects;

public class RequestKey {
    private String url;
    private String method;

    public RequestKey(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestKey that = (RequestKey) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }

    // Getter 메서드들
    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}
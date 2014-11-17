package com.swift.router;

import com.swift.Request;
import com.swift.Response;

public abstract class BaseRoute implements Route {
    protected Request request;
    protected Response response;

    protected BaseRoute() {

    }

    protected BaseRoute(Request request) {
        this.request = request;
        this.response = request.getResponse();
    }

    public boolean isMatch(Request request) {
        this.request = request;
        this.response = request.getResponse();
        return false;
    }
}

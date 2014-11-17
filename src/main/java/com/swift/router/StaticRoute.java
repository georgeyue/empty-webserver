package com.swift.router;

import com.swift.Request;
import com.swift.router.Route;

public class StaticRoute implements Route {
    private Request request;
    private String urlRegex;

    public StaticRoute(Request request) {
        this.request = request;
        urlRegex = "^/.*";
    }

    @Override
    public void handle() {
    }

    @Override
    public boolean isMatch() {
        return request.getUrl().matches(urlRegex);
    }
}

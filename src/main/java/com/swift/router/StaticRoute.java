package com.swift.router;

import com.swift.Request;
import com.swift.router.Route;

public class StaticRoute implements Route {
    private String urlRegex;
    private Request request;

    public StaticRoute() {
        urlRegex = "^/.*";
    }

    public void handle() {
    }

    public boolean isMatch(Request request) {
        this.request = request;
        return request.getUrl().matches(urlRegex);
    }
}

package com.swift.router;

import com.swift.Request;

public class StaticRoute extends BaseRoute {
    private String urlRegex;

    public StaticRoute() {
        super();
        urlRegex = "^/.*";
    }

    public void handle() {
    }

    public boolean isMatch(Request request) {
        super.isMatch(request);
        return request.getUrl().matches(urlRegex);
    }
}

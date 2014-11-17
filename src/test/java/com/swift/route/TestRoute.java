package com.swift.route;

import com.swift.Request;
import com.swift.router.Route;

public class TestRoute implements Route {
    private boolean isRun = false;
    private String urlToHandle;

    public TestRoute(String url) {
        urlToHandle = url;
    }

    @Override
    public void handle() {
        isRun = true;
    }

    @Override
    public boolean isMatch(Request request) {
        return request.getUrl().matches(urlToHandle);
    }

    public boolean isRun() {
        return isRun;
    }
}

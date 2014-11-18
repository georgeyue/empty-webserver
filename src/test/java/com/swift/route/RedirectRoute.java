package com.swift.route;

import com.swift.Request;
import com.swift.router.BaseRoute;
import com.swift.router.Route;

import java.io.IOException;

public class RedirectRoute extends BaseRoute {
    @Override
    public void handle() throws IOException {
        response.setStatusCode(302);
        response.sendHeader("Location", request.getHeader("host"));
        response.send();
    }

    @Override
    public boolean isMatch(Request request) {
        super.isMatch(request);
        return request.getUrl().equals("/redirect")
                && request.getMethod().equals("GET");
    }
}

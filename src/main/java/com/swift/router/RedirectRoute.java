package com.swift.router;

import com.swift.Request;
import com.swift.router.BaseRoute;
import com.swift.router.Route;

import java.io.IOException;

public class RedirectRoute extends BaseRoute {
    @Override
    public void handle() throws IOException {
        String host = request.getHeader("host");
        host = host == null ? "/" : host + "/";

        response.setHeader("Location", host);
        response.send(302);
    }

    @Override
    public boolean isMatch(Request request) {
        super.isMatch(request);
        return request.getUrl().equals("/redirect")
                && request.getMethod().equals("GET");
    }
}

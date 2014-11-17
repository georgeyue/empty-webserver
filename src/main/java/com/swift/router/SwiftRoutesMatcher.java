package com.swift.router;

import com.swift.Request;
import com.swift.Response;

import java.io.IOException;

public class SwiftRoutesMatcher extends RoutesMatcher {

    @Override
    public void constructRoutes() {
        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {
                response.sendHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
                response.send();
            }

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getUrl().equals("/method_options")
                        && request.getMethod().equals("OPTIONS");
            }
        });


        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {
                response.setNotFoundHeader();
                response.send();
            }

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getUrl().equals("/foobar")
                        && this.request.getMethod().equals("GET");
            }
        });



    }
}

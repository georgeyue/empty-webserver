package com.swift.router;

import com.swift.Request;
import com.swift.Response;
import com.swift.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SwiftRoutesMatcher extends RoutesMatcher {

    @Override
    public void constructRoutes() {
        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {

            }

            @Override
            public boolean isMatch(Request request) {
                if (!request.getUrl().equals("/logs")) {
                    Server.logs.add(request.getRequestLine());
                }
                return false;
            }
        });

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

        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {
                response.setMethodNotAllowed();
                response.send();
            }

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getMethod().equals("PUT")
                        && request.getUrl().equals("/file1");
            }
        });

        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {
                response.setMethodNotAllowed();
                response.send();
            }

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getMethod().equals("POST")
                        && request.getUrl().equals("/text-file.txt");
            }
        });

        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {
                Map<String,String> qs = request.getQueryParams();
                String nl = String.format("%n");
                StringBuilder str = new StringBuilder();
                for(Map.Entry<String, String> p : qs.entrySet()) {
                    str.append(p.getKey() + " = " + p.getValue() + nl);
                }
                response.setResponseBody(str.toString());
                response.send(200);
            }

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getPathname().equals("/parameters")
                        && request.getMethod().equals("GET");
            }
        });

        routes.add(new FormRoute());
        routes.add(new RedirectRoute());
        routes.add(new AuthenticateRoute());
        routes.add(new DirectoryRoute());
        routes.add(new StaticRoute());
    }
}

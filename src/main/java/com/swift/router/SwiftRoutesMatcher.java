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

        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {
                response.send(200);
            }

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getMethod().equals("POST")
                        && request.getUrl().equals("/form");
            }
        });


        routes.add(new BaseRoute() {
            @Override
            public void handle() throws IOException {
                response.send(200);
            }

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getMethod().equals("PUT")
                        && request.getUrl().equals("/form");
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
				System.out.println("i am here 1");
				if (!authenticateUser()) {
					response.setResponseBody("Authentication required");
					response.setUnauthorizedUser();
					response.send();
				} else {
					// response.setResponseBody("GET /logs HTTP/1.1");
					//response.setResponseBody("Authorized!!!");
					//response.setAuthorizedUser();
					System.out.println("i am here");
					response.send(200);
				}
			}

            @Override
            public boolean isMatch(Request request) {
                super.isMatch(request);
                return request.getMethod().equals("GET")
                        && request.getUrl().equals("/logs");
            }
        });
    
    
    
}

    
    
 
}
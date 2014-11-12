package com.swift;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler {

    private Request request;

//    TODO let's add routes to define behaviour based on routes
//    private Routes routes;

    public RequestHandler(Request request) {
        this.request = request;
    }

    public void process() throws IOException {
        Response  response = request.getResponse();
        String url = request.getUrl();

        // TODO this needs to be extracted out to have routes handle this
        if (request.getMethod().equals("GET") && url.equals("/foobar")) {
            response.setNotFoundHeader();
        } else if (request.getMethod().equals("GET") && url.equals("/")) {
            response.ok();
        } else {
            // TODO nothing matches!
        }
        response.send();
    }
}

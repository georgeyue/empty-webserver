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
        // TODO should do more
        response.setNotFoundHeader();
        response.send();
    }
}

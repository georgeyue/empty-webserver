package com.swift;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler {

    private Response response;

//    TODO let's add routes to define behaviour based on routes
//    private Routes routes;

    public RequestHandler(Response response) {
        this.response = response;
    }

    public void process() throws IOException {
        // TODO should do more
        response.setNotFoundHeader();
        response.send();
    }
}

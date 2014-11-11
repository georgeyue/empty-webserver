package com.swift;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by gyue on 11/11/14.
 */
public class RequestHandler {

    private Response response;

    public RequestHandler(Response response) {
        this.response = response;
    }

    public void process() throws IOException {
        // TODO should do more
        response.setNotFoundHeader();
        response.send();
    }
}

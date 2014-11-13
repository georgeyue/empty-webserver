package com.swift;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Request {
    private Response response;
    private Socket socket;

    private String method;

    private String url;
    public String getProtocol() {
        return protocol;
    }

    private String protocol;

    public Request(Socket socket) throws IOException {
        this.socket = socket;
        this.response = new Response(socket);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String headerString = in.readLine();
        String[] hd = headerString.split(" ");


        // TODO check hd to tokenized with 3 items?? wtf bad implementation yo!
        method = hd[0];
        url = hd[1];
        protocol = hd[2];  //isn't this data
    }

    public Socket getSocket() {
        return socket;
    }

    public String getMethod() {
        return method;
    }

    public Response getResponse() {
        return response;
    }

    public String getUrl() {
        return url;
    }
}

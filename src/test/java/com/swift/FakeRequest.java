package com.swift;

import java.io.IOException;
import java.net.Socket;

public class FakeRequest extends Request {
    public FakeRequest(Socket socket) throws IOException {
        super(socket);
    }

    public FakeRequest() throws IOException {
        super(new FakeSocket());
    }

    public void setRequestLine(String line) {
        super.requestLine = line;
    }

    public void setUrl(String url) {
        super.setUrl(url);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

package com.swift;

import java.io.IOException;
import java.net.Socket;

public class FakeRequest extends Request {
    private String url;

    public FakeRequest(Socket socket) throws IOException {
        super(socket);
    }

    public FakeRequest() throws IOException {
        super(new FakeSocket());
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

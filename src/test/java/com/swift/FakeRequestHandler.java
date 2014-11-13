package com.swift;

import java.io.IOException;
import java.net.Socket;

public class FakeRequestHandler extends RequestHandler {
    public Socket handledSocket;

    public FakeRequestHandler() throws IOException {
        super(new Request(new FakeSocket()));
    }

    @Override
    public void handleRequestFrom(Socket socket) throws IOException {
        this.handledSocket = socket;
    }
}

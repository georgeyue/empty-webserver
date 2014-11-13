package com.swift;

import java.io.IOException;
import java.net.Socket;

public class FakeServerSocket implements SwiftServerSocket {
    private boolean isClosed;

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() {
        isClosed = true;
    }

    @Override
    public Socket accept() throws IOException {
        return null;
    }
}

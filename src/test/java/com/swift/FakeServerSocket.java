package com.swift;

import java.io.IOException;
import java.net.Socket;

public class FakeServerSocket implements SwiftServerSocket {
    private boolean isClosed;
    private Socket socket;
    private int numberOfConnections;

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() {
        isClosed = true;
    }

    public void open() {
        isClosed = false;
    }

    public void setNumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }

    @Override
    public Socket accept() throws IOException {
        numberOfConnections--;
        if (numberOfConnections <= 0)
            this.close();
        return this.socket;
    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

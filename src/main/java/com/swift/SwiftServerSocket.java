package com.swift;
import java.io.IOException;
import java.net.Socket;

public interface SwiftServerSocket {

    public boolean isClosed();
    public void close() throws IOException;
    public Socket accept() throws IOException;
}

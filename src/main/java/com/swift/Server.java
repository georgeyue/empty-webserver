package com.swift;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket serverSocket;
    private Socket socket;
    private int portNumber;

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverSocket = new ServerSocket(portNumber);
    }

    public static void main(String[] args) {
    }

	public void stop() throws IOException {
        if (!serverSocket.isClosed())
            serverSocket.close();
    }

    public ServerSocket getSocket() {
        return serverSocket;
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public void run() throws IOException {
        if (serverSocket.isClosed())
            return;

        socket = serverSocket.accept();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("HTTP/1.1 404 Not Found");
        out.close();
    }
}

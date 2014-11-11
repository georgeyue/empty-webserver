package com.swift;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private httpServerSocket serverSocket;
    private Socket socket;
    private int portNumber;
    private boolean stopped = false;

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverSocket = new httpServerSocket(portNumber);
    }

    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server(Integer.parseInt(args[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

	public void stop() throws IOException {
        stopped = true;
        if (!serverSocket.isClosed())
            serverSocket.close();
    }

    public httpServerSocket getSocket() {
        return serverSocket;
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public void run() throws IOException {

        while(!stopped && !serverSocket.isClosed()) {
            socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("HTTP/1.1 404 Not Found");
            out.close();
        }
    }

}

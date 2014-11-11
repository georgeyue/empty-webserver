package com.swift;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket serverSocket;
    private Socket socket;
    private int portNumber;
    private boolean stopped = false;
    private String directory = "";

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverSocket = new ServerSocket(portNumber);
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

    public ServerSocket getSocket() {
        return serverSocket;
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public void run() throws IOException {

        while(!stopped && !serverSocket.isClosed()) {
            socket = serverSocket.accept();
            Response  response = new Response(socket);

            RequestHandler handler = new RequestHandler(response);
            handler.process();
        }
    }

    public void setDirectory(String dir) {
        directory = dir;
    }

    public String getDirectory() {
        return directory;
    }
}

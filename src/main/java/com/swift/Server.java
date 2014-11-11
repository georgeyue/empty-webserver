package com.swift;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server implements Runnable {

	private ServerSocket serverSocket;
    private Socket socket;
    private int portNumber;
    private boolean isStopped = false;

    public Server(int portNumber) {
        this.portNumber = portNumber;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println("unable to create ServerSocket.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }

	public void stop() {
        try {
            isStopped = true;
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getSocket() {
        return serverSocket;
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public void run() {
        try {
            while (!isStopped) {
                socket = serverSocket.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("HTTP/1.1 404 Not Found");
                out.close();
            }
        } catch (SocketException e) {
            // FIXME why does it throw exception when the socket is not
            // closed and code execution gets here?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

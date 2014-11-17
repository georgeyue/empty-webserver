package com.swift;

import com.swift.router.*;

import java.io.*;
import java.net.Socket;

public class Server {

	private httpServerSocket serverSocket;
    private Socket socket;
    private boolean stopped = false;
    private static String directory = "";

    public Server(int portNumber, String directoryToUse) throws IOException {
        serverSocket = new httpServerSocket(portNumber);
        this.directory = directoryToUse;
    }

    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server(Integer.parseInt(args[1]), args[3]);
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
    
    public boolean isStopped() {
    	return this.stopped;
    }

    public void run() throws IOException {

        while(!isStopped() && !serverSocket.isClosed()) {
            socket = serverSocket.accept();
            Request request = new Request(socket);
            RoutesMatcher routes = new SwiftRoutesMatcher();
            routes.add(new DirectoryRoute());
            routes.constructRoutes();

            RequestHandler handler = new RequestHandler(request, routes);
            handler.process();
        }
    }

    public void setDirectory(String dir) {
        directory = dir;
    }

    public static String getDirectory() {
        return directory;
    }
}

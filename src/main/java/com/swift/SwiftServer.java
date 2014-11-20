package com.swift;

import com.swift.router.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SwiftServer {

	private httpServerSocket serverSocket;
    private Socket socket;
    private boolean stopped = false;
    private static String directory = "";
    private static boolean debug = false;
    public static ArrayList<String> logs = new ArrayList<String>();

    public SwiftServer(int portNumber, String directoryToUse) throws IOException {
        serverSocket = new httpServerSocket(portNumber);
        this.directory = directoryToUse;
    }

    public static void sout(Object o) {
        if (debug)
            System.out.println(o);
    }

	public void stop() throws IOException {
        stopped = true;
        if (!serverSocket.isClosed())
            serverSocket.close();
    }

    public httpServerSocket getSocket() {
        return serverSocket;
    }
    
    public boolean isStopped() {
    	return this.stopped;
    }

    public void run() throws IOException {

        while(!isStopped() && !serverSocket.isClosed()) {
            socket = serverSocket.accept();

            RoutesMatcher routes = new SwiftRoutesMatcher();
            routes.constructRoutes();

            RequestHandler handler = new RequestHandler(socket, routes);
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

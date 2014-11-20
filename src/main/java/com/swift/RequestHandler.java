package com.swift;

import com.swift.router.RoutesMatcher;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler {

    private Request request;
    private String rootDirectory;
    private RoutesMatcher routes;

//    TODO let's add routes to define behaviour based on routes
//    private Routes routes;

    public RequestHandler(Socket socket, RoutesMatcher routes) throws IOException {
        this.request = new Request(socket);
        this.rootDirectory = SwiftServer.getDirectory();
        this.routes = routes;
    }

    public void process() throws IOException {
        routes.processRequest(request);
    }
    
    public boolean fileExists() {
    	String fileToCheck = this.request.getUrl();
    	Path pathToCheck = FileSystems.getDefault().getPath(rootDirectory, fileToCheck);
		return Files.exists(pathToCheck);
    }
}

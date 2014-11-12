package com.swift;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler {

    private Request request;
    private String rootDirectory;

//    TODO let's add routes to define behaviour based on routes
//    private Routes routes;

    public RequestHandler(Request request) {
        this.request = request;
        this.rootDirectory = System.getProperty("user.dir");
    }

    public void process() throws IOException {
        Response  response = request.getResponse();
        String url = request.getUrl();
        
        // TODO this needs to be extracted out to have routes handle this
        if (request.getMethod().equals("GET") && url.equals("/foobar")) {
            response.setNotFoundHeader();
        } else if (request.getMethod().equals("GET") && url.equals("/")) {
            response.ok();
        } else {
            // TODO nothing matches!
        }
        response.send();
    }
    
    public boolean fileExists() {
    	String fileToCheck = this.request.getUrl();
    	Path pathToCheck = FileSystems.getDefault().getPath(rootDirectory, fileToCheck);
		return Files.exists(pathToCheck);
    }
}

package com.swift;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RequestHandler {

    private Request request;
    private String rootDirectory;

//    TODO let's add routes to define behaviour based on routes
//    private Routes routes;

    public RequestHandler(Request request) {
        this.request = request;
        this.rootDirectory = Server.getDirectory();
    }

    public void process() throws IOException {
        Response  response = request.getResponse();
        String url = request.getUrl();
        
        // TODO this needs to be extracted out to have routes handle this
        if (url.equals("/method_options")) {
            if (request.getMethod().equals("OPTIONS"))
                response.sendHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
            response.send();
        } else if (request.getMethod().equals("GET") && url.equals("/foobar")) {
            response.setNotFoundHeader();
        } else if (request.getMethod().equals("GET") && url.equals("/")) {
        	if(fileExists())
                response.setContentType("text/plain");
        		response.setResponseBody(directoryListing());
            response.ok();
        } else if (request.getMethod().equals("POST") && url.equals("/form")) {
            response.ok();
        }else if (request.getMethod().equals("PUT") && url.equals("/form")) {
            response.ok();
        }else {
            // TODO nothing matches!
        }
        response.send();
    }
    
    public boolean fileExists() {
    	String fileToCheck = this.request.getUrl();
    	Path pathToCheck = FileSystems.getDefault().getPath(rootDirectory, fileToCheck);
		return Files.exists(pathToCheck);
    }
    
    public String directoryListing() {
    	String directoryList = null;
    	Path directory = FileSystems.getDefault().getPath(rootDirectory);
    	try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
			for(Path file : stream) {
				directoryList += file.getFileName().toString() + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return directoryList;
    }
}

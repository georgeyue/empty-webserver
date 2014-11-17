package com.swift;

import com.swift.router.RoutesMatcher;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RequestHandler {

    private Request request;
    private String rootDirectory;
    private RoutesMatcher routes;

//    TODO let's add routes to define behaviour based on routes
//    private Routes routes;

    public RequestHandler(Request request, RoutesMatcher routes) {
        this.request = request;
        this.rootDirectory = Server.getDirectory();
        this.routes = routes;
    }

    public void process() throws IOException {
        Response  response = request.getResponse();
        String url = request.getUrl();

        routes.processRequest(request);
        if (routes.requestIsProccessed()) {
            return;
        }

        // TODO this needs to be extracted out to have routes handle this
        if (request.getMethod().equals("GET") && url.equals("/")) {
        	if(fileExists()) {
                response.setContentType("text/html");
        		response.setResponseBody(directoryListing());
        	}
            response.ok();
//        } else if (request.getMethod().equals("POST") && url.equals("/form")) {
//            response.ok();
        }else if (request.getMethod().equals("PUT") && url.equals("/form")) {
            response.ok();
        }else if (request.getMethod().equals("PUT") && url.equals("/file1")) {
        	response.setMethodNotAllowed();
        }else if (request.getMethod().equals("POST") && url.equals("/text-file.txt")) {
        	response.setMethodNotAllowed();
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
    	String directoryList = "<html><head><title>Directory</title></head><body>";
    	Path directory = FileSystems.getDefault().getPath(rootDirectory);
    	try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
			for(Path file : stream) {
				if(!file.toFile().isHidden())
					directoryList += "<a href=\"/" + file.getFileName().toString() + "\">" + file.getFileName().toString() + "</a><br/>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	directoryList += "</body></html>";
    	return directoryList;
    }
}

package com.swift.router;

import java.io.IOException;
import com.swift.FileFinder;
import com.swift.Request;
import com.swift.SwiftServer;

public class StaticRoute extends BaseRoute {
    private String urlRegex;

    public StaticRoute() {
        super();
        urlRegex = "^/.*";
    }

    public void handle() throws IOException {
    	FileFinder finder = new FileFinder();
    	String[] urlParts = request.getUrl().split("/");

    	finder.setRootDirectory(SwiftServer.getDirectory());
    	finder.setFile(urlParts[urlParts.length-1]);
    	if(finder.exists()) {
    		response.setContentType(finder.getMimeType());
    		response.setContentLength(finder.getFileContents().length);
    		response.setResponseBodyBytes(finder.getFileContents());
    		response.sendBinary(200);
    	}
    	else {
    		response.setContentType("text/html");
    		response.setNotFoundHeader();
    		response.send(404);
    	}
    }

    public boolean isMatch(Request request) {
        super.isMatch(request);
        return request.getUrl().matches(urlRegex);
    }
    
}

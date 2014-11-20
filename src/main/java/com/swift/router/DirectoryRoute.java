package com.swift.router;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.swift.Request;
import com.swift.SwiftServer;


public class DirectoryRoute extends BaseRoute {
	@Override
	public void handle() throws IOException {
		response.setContentType("text/html");
		response.setResponseBody(directoryListing());
		response.send(200);
	}
	
	@Override
	public boolean isMatch(Request request) {
		super.isMatch(request);
		return request.getMethod().equals("GET") 
				&& request.getUrl().equals("/");
	}
	
    public String directoryListing() {
    	String directoryList = "<html><head><title>Directory</title></head><body>";
    	Path directory = FileSystems.getDefault().getPath(SwiftServer.getDirectory());
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

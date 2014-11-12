package com.swift;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

import static org.junit.Assert.*;

public class RequestHandlerTest {
	RequestHandler requestHandler;
	
	@Test
	public void shouldFindFilePath() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		
		assertTrue(requestHandler.fileExists());
	}
}
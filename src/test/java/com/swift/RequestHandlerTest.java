package com.swift;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestHandlerTest {
	RequestHandler requestHandler;
	
	@Test
	public void shouldNotFindFilePathWithNoBaseDirectory() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		assertFalse(requestHandler.fileExists());
	}
	
	@Test
	public void canListDirectories() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET / HTTP/1.1");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		assertNotNull(requestHandler.directoryListing());
	}
	
	@Test
	public void shouldSimplePost() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("POST /form My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		requestHandler.process();
		assertEquals(200,request.getResponse().getStatusCode());
	}
	
	@Test
	public void shouldSimplePut() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("PUT /form My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		requestHandler.process();
		assertEquals(200, request.getResponse().getStatusCode());
	}
}
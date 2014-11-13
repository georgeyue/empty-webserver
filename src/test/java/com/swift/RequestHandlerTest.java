package com.swift;
import org.junit.Test;
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
		assertEquals(200,request.getResponse().getStatusCode());
	}
	
	@Test
	public void shouldMethodNotAllowed405WithPut() throws Exception{
		FakeSocket socket = new FakeSocket();
		socket.setText("PUT /file1 My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		requestHandler.process();
		assertEquals(405,request.getResponse().getStatusCode());
	}
	
	@Test
	public void shouldMethodNotAllowedWithPost() throws Exception{
		FakeSocket socket = new FakeSocket();
		socket.setText("POST /text-file.txt My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		requestHandler.process();
		assertEquals(405,request.getResponse().getStatusCode());
	}
}
package com.swift;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestHandlerShouldTest {
	RequestHandler requestHandler;
	
	@Test
//	public void shouldNotFindFilePathWithNoBaseDirectory() throws Exception {
	public void fileNotFoundDueToLackOfBaseDirectory() throws Exception {
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
	public void simplePost() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("POST /form My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		requestHandler.process();
		assertEquals(200,request.getResponse().getStatusCode());
	}
	
	@Test
	public void simplePut() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("PUT /form My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request);
		requestHandler.process();
		assertEquals(200, request.getResponse().getStatusCode());
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
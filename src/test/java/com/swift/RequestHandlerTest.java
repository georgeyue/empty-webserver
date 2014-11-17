package com.swift;
import com.swift.router.RoutesMatcher;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestHandlerTest {
	RequestHandler requestHandler;
    RoutesMatcher routes;

    @Before
    public void setUp() {
        routes = new RoutesMatcher();
    }
	@Test
	public void shouldNotFindFilePathWithNoBaseDirectory() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request, routes);
		assertFalse(requestHandler.fileExists());
	}
	
	@Test
	public void canListDirectories() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET / HTTP/1.1");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request, routes);
		assertNotNull(requestHandler.directoryListing());
	}
	
	@Test
	public void shouldSimplePost() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("POST /form My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request, routes);
		requestHandler.process();
		assertEquals(200,request.getResponse().getStatusCode());
	}
	
	@Test
	public void shouldSimplePut() throws Exception {
        FakeSocket socket = new FakeSocket();
        socket.setText("PUT /form My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request, routes);
		requestHandler.process();
		assertEquals(200, request.getResponse().getStatusCode());
	}
	
	@Test
	public void shouldMethodNotAllowed405WithPut() throws Exception{
		FakeSocket socket = new FakeSocket();
		socket.setText("PUT /file1 My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request, routes);
		requestHandler.process();
		assertEquals(405,request.getResponse().getStatusCode());
	}
	
	@Test
	public void shouldMethodNotAllowedWithPost() throws Exception{
		FakeSocket socket = new FakeSocket();
		socket.setText("POST /text-file.txt My=data");
		Request request = new Request(socket);
		requestHandler = new RequestHandler(request, routes);
		requestHandler.process();
		assertEquals(405,request.getResponse().getStatusCode());
	}
}
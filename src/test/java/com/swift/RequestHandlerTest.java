package com.swift;
import com.swift.router.RoutesMatcher;
import com.swift.router.SwiftRoutesMatcher;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestHandlerTest {
	RequestHandler requestHandler;
    RoutesMatcher routes;

    @Before
    public void setUp() {
        routes = new SwiftRoutesMatcher();
        routes.constructRoutes();
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
}



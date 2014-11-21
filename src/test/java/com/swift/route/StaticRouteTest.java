package com.swift.route;

import com.swift.FakeRequest;
import com.swift.FakeSocket;
import com.swift.router.FormRoute;
import com.swift.router.Route;
import com.swift.router.StaticRoute;
import com.swift.storage.StaticStorage;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StaticRouteTest {
	FakeSocket socket;
    FakeRequest request;
    StaticRoute route;

    @Before
    public void setup() throws IOException {
        socket = new FakeSocket();
        request = new FakeRequest(socket);
        route = new StaticRoute();
    }

    @Test
    public void createNewRoute() throws IOException {
        assertNotNull(route);
    }

    @Test
    public void testsForMatch() throws IOException {
        request.setUrl("/foobar");
        assertTrue(route.isMatch(request));
    }
    
    @Test
	public void handlesFileRequestIfMissing() throws Exception {
		request.setUrl("/foobaz");
		route.isMatch(request);
		route.handle();
		assertTrue(socket.getText().contains(String.format("HTTP/1.1 404 Not Found")));
	}

}
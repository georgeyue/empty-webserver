package com.swift.route;

import com.swift.FakeRequest;
import com.swift.FakeSocket;
import com.swift.router.RedirectRoute;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RedirectRouteTest {

    @Test
    public void testIsMatch() throws Exception {
        RedirectRoute route = new RedirectRoute();

        FakeRequest request = new FakeRequest();
        request.setRequestLine("GET /redirect HTTP/1.1");

        assertTrue(route.isMatch(request));
    }

    @Test
    public void testResponseLineIsARedirect() throws IOException {
        RedirectRoute route = new RedirectRoute();
        FakeSocket socket = new FakeSocket();
        FakeRequest request = new FakeRequest(socket);

        request.setRequestLine("GET /redirect HTTP/1.1");
        request.setHeader("banan", "banana");
        route.isMatch(request);
        route.handle();

        assertEquals(String.format("HTTP/1.1 302 Found%nLocation: /\r\n"), socket.getText());
    }

    @Test
    public void redirectWithHostHeaderAsLocation() throws IOException {
        RedirectRoute route = new RedirectRoute();
        FakeSocket socket = new FakeSocket();
        FakeRequest request = new FakeRequest(socket);

        request.setRequestLine("GET /redirect HTTP/1.1");
        request.setHeader("host", "http://localhost:5000");
        route.isMatch(request);
        route.handle();

        assertEquals(String.format("HTTP/1.1 302 Found%nLocation: http://localhost:5000/\r\n"), socket.getText());
    }

    @Test
    public void notRespondToOtherRequests() throws IOException {
        RedirectRoute route = new RedirectRoute();

        FakeRequest request = new FakeRequest();
        request.setRequestLine("GET /banana HTTP/1.1");

        assertFalse(route.isMatch(request));
    }
}
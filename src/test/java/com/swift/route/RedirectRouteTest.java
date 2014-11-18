package com.swift.route;

import com.swift.FakeRequest;
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
    public void notRespondToOtherRequests() throws IOException {
        RedirectRoute route = new RedirectRoute();

        FakeRequest request = new FakeRequest();
        request.setRequestLine("GET /banana HTTP/1.1");

        assertFalse(route.isMatch(request));
    }
}
package com.swift;
import org.junit.Test;
import java.io.IOException;
import java.net.URLDecoder;

import static org.junit.Assert.*;
public class RequestTest {


    @Test
    public void initWithSocket() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET / HTTP");
        Request req = new Request(socket);
        assertNotNull(req.getSocket());
    }

    @Test
    public void getRequestMethod() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET / HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("GET", req.getMethod());
    }

    @Test
    public void getRequestMethodWhenNotProvided() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("");

        Request req = new Request(socket);
        assertEquals("", req.getMethod());
    }

    @Test
    public void getRequestUrl() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("/foobar", req.getUrl());
    }

    @Test
    public void returnsRequestProtocol() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("HTTP/1.1", req.getProtocol());
    }


    @Test
    public void returnRequestLine() throws IOException {
        String line = "GET / HTTP";
        FakeSocket socket = new FakeSocket();
        socket.setText(line);
        Request req = new Request(socket);

        assertEquals(line, req.getRequestLine());
    }

    @Test
    public void shouldParsePathname() throws IOException {
        String line = "GET /apple?name=bean%20stalk HTTP/1.1";
        FakeRequest req = new FakeRequest();
        req.setRequestLine(line);
        assertEquals("/apple?name=bean stalk", req.getUrl());
    }
}
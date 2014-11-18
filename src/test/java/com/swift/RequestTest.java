package com.swift;
import org.junit.Test;
import java.io.IOException;
import java.util.Map;

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
    public void shouldGetPathname() throws IOException {
        String line = "GET /apple?name=bean%20stalk HTTP/1.1";
        FakeRequest req = new FakeRequest();
        req.setRequestLine(line);
        assertEquals("/apple", req.getPathname());
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

    @Test
    public void shouldDecodeString() throws IOException {
        FakeRequest req = new FakeRequest();
        assertEquals(" ", req.decode("%20"));
    }

    @Test
    public void shouldExtractQueryParam() throws IOException {
        String line = "GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP";
        FakeRequest req = new FakeRequest();
        req.setRequestLine(line);

        Map<String, String> qs = req.getQueryParams();
        assertEquals( "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", qs.get("variable_1"));
        assertEquals( "stuff", qs.get("variable_2") );
    }
}
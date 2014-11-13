package com.swift;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;
public class RequestTest {


    @Test
    public void initWithSocket() throws IOException {
        Request req = new Request( new FakeSocket() );
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
    public void getRequestUrl() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("/foobar", req.getUrl());
    }

}
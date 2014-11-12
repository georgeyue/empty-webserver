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
        socket.setRequestHeader("GET / HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("GET", req.method());
    }
}
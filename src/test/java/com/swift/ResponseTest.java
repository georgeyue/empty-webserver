package com.swift;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void shouldSetHeaderNotFound() throws Exception {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.setNotFoundHeader();
        response.send();

        assertEquals(404, response.getStatusCode());
        assertEquals(String.format("HTTP/1.1 404 Not Found%n"), socket.getText());
    }

    @Test
    public void shouldResponseWithOk() throws IOException {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.ok();
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%n"), socket.getText());
    }
    
    

}
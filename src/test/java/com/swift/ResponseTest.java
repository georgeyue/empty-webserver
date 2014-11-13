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
    
    @Test
	public void shouldListDirectory() throws Exception {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.setContentType("text/directory");
        response.ok();
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Type: text/directory%n"), socket.getText());
	}
    
    @Test
	public void shouldListDirectoryContents() throws Exception {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.setContentType("text/directory");
        response.setResponseBody("this is my body");
        response.ok();
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Type: text/directory%n%nthis is my body%n"), socket.getText());
	}
    

    @Test
    public void shouldSetHeader() throws IOException {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.ok();
        response.sendHeader("Allowed", "GET,HEAD");
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nAllowed: GET,HEAD%n"), socket.getText());
    }

    @Test
    public void sendResponseLineOnSend() throws IOException {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%n"), socket.getText());
    }

}
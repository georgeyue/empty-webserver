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
        response.sendResponseLine(200);
        response.send();
        assertEquals(String.format("HTTP/1.1 200 OK%n"), socket.getText());
    }
    @Test
    public void shouldUnauthorizedUserWith401() throws IOException {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.sendResponseLine(401);
        response.send();
       assertEquals(String.format("HTTP/1.1 401 Error%n"), socket.getText());
    }

    @Test
	public void shouldListDirectory() throws Exception {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.setContentType("text/directory");
        response.sendResponseLine(200);
        response.send();
        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Type: text/directory%n"), socket.getText());
	}
    
    @Test
	public void shouldListDirectoryContents() throws Exception {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.setContentType("text/directory");
        response.setResponseBody("this is my body");
        response.sendResponseLine(200);
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Type: text/directory%n%nthis is my body"), socket.getText());
	}
    
    @Test
	public void shouldHaveContentLengthInHeader() throws Exception {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.setContentLength(15);
        response.sendResponseLine(200);
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Length: 15%n"), socket.getText());
	}

    @Test
    public void shouldSetHeader() throws IOException {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
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

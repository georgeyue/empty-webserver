package com.swift;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ResponseShouldTest {

    @Test
    public void setHeaderNotFound() throws Exception {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.setNotFoundHeader();
        response.send();

        assertEquals(404, response.getStatusCode());
        assertEquals(String.format("HTTP/1.1 404 Not Found%n"), socket.getText());
    }

    @Test
    public void respondWithOk() throws IOException, ResponseLineSentException {
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

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Type: text/directory%nthis is my body%n"), socket.getText());
	}
    

    @Test
    public void shouldSetHeader() throws IOException, ResponseLineSentException {
        FakeSocket socket = new FakeSocket();
        Response response = new Response(socket);
        response.ok();
        response.sendHeader("Allowed", "GET");
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nAllowed: GET%n"), socket.getText());
    }

}
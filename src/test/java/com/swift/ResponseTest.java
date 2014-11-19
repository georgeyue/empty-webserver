package com.swift;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ResponseTest {

	private FakeSocket socket;
	private Response response;
	
	@Before
	public void setUp() throws IOException {
        socket = new FakeSocket();
        response = new Response(socket);		
	}
	
    @Test
    public void shouldSetHeaderNotFound() throws Exception {
        response.setNotFoundHeader();
        response.send();
        assertEquals(404, response.getStatusCode());
        assertEquals(String.format("HTTP/1.1 404 Not Found%n"), socket.getText());
    }

    @Test
    public void shouldResponseWithOk() throws IOException {
        response.sendResponseLine(200);
        response.send();
        assertEquals(String.format("HTTP/1.1 200 OK%n"), socket.getText());
    }
    
    @Test
	public void shouldResponsdWithPartial() throws Exception {
		response.sendResponseLine(206);
		response.send();
		assertEquals(String.format("HTTP/1.1 206 Partial Content%n"), socket.getText());
	}
    
    @Test
	public void shouldListDirectory() throws Exception {
        response.setContentType("text/directory");
        response.sendResponseLine(200);
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Type: text/directory%n"), socket.getText());
	}
    
    @Test
	public void shouldListDirectoryContents() throws Exception {
        response.setContentType("text/directory");
        response.setResponseBody("this is my body");
        response.sendResponseLine(200);
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Type: text/directory%n%nthis is my body"), socket.getText());
	}
    
    @Test
	public void shouldHaveContentLengthInHeader() throws Exception {
        response.setContentLength(15);
        response.sendResponseLine(200);
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nContent-Length: 15%n"), socket.getText());
	}

    @Test
    public void shouldSetHeader() throws IOException {
        response.sendHeader("Allowed", "GET,HEAD");
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%nAllowed: GET,HEAD%n"), socket.getText());
    }

    @Test
    public void sendResponseLineOnSend() throws IOException {
        response.send();

        assertEquals(String.format("HTTP/1.1 200 OK%n"), socket.getText());
    }

}
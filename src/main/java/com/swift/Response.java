package com.swift;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Response {
    private Socket socket;
    private int statusCode;
    private String contentType;
	private String responseBody;
    private PrintWriter out;

    private boolean responseLineSent = false;

    public Response(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
    }
    
    public void setMethodNotAllowed() throws IOException {
    	sendResponseLine(405);
    }
    
    public void sendResponseLine(int i) {
        if (responseLineSent)
            return;

        responseLineSent = true;
        statusCode = i;

        switch(statusCode) {
            case 404:
                out.println("HTTP/1.1 404 Not Found");
                break;
            case 405:
            	out.println("HTTP/1.1 405 Method Not Allowed");
            	break;
            case 302:
                out.println("HTTP/1.1 302 Found");
                break;
            case 200:
            	out.println("HTTP/1.1 200 OK");
            	break;
            default:
                out.println("HTTP/1.1 200 OK");
              
        }

        out.flush();
    }

    public void setNotFoundHeader() throws IOException {
        sendResponseLine(404);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void send() throws IOException {
        if (!responseLineSent)
            sendResponseLine(222);
        if(getContentType() != null)
            out.println("Content-Type: " + getContentType());
        if(getResponseBody() != null)
            out.print(String.format("%n") + getResponseBody());
        out.flush();
        socket.close();
    }

    public void send(int statusCode) throws IOException {
        sendResponseLine(statusCode);
        send();
    }

    public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setResponseBody(String body) {
		this.responseBody = body;
	}

	public String getResponseBody() {
		return this.responseBody;
	}

    public void sendHeader(String key, String value) throws IOException {
        if (!responseLineSent)
            sendResponseLine(statusCode);

        out.println(key + ": " + value);
        out.flush();
    }
}

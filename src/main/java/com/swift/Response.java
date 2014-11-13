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

    public Response(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
    }

    public void setNotFoundHeader() throws IOException {
        setStatusCode(404);

        out.println("HTTP/1.1 404 Not Found");
        out.flush();
    }

    private void setStatusCode(int i) {
        this.statusCode = i;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void send() throws IOException {
        socket.close();
    }

    public void ok() throws IOException {
        setStatusCode(200);

        out.println("HTTP/1.1 200 OK");
        if(getContentType() != null)
        	out.println("Content-Type: " + getContentType());
        if(getResponseBody() != null)
        	out.println(getResponseBody());
        out.flush();
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
        out.println(key + ": " + value);
        out.flush();
    }
}

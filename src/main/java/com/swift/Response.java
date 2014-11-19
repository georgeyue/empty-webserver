package com.swift;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Response {
    private Socket socket;
    private int statusCode;
    private String contentType;
	private String responseBody;
	private int contentLength;
    private PrintWriter out;
    
    private byte[] responseBodyBytes;
    private OutputStream os;

    private boolean responseLineSent = false;
	private int[] contentRange;

    public Response(Socket socket) throws IOException {
        this.socket = socket;
        os = socket.getOutputStream();
        out = new PrintWriter(
        	    new BufferedWriter(
        	        new OutputStreamWriter(os)
        	    )
        );

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
            case 200:
            	out.println("HTTP/1.1 200 OK");
            	break;
            case 206:
            	out.println("HTTP/1.1 206 Partial Content");
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
        if(getContentLength() > 0)
        	out.println("Content-Length: " + getContentLength());
        if(getContentRange() != null) {
        	out.println("Content-Range: bytes " + getContentRange()[0] + "-" + getContentRange()[1] + "/" + getContentLength());
        }
        if(getResponseBody() != null)
        	out.print(String.format("%n") + getResponseBody());
        out.flush();
        socket.close();
    }

    public void send(int statusCode) throws IOException {
        sendResponseLine(statusCode);
        send();
    }

	public void sendBinary(int statusCode) throws IOException {
        sendResponseLine(statusCode);
        if(getContentType() != null)
            os.write(("Content-Type: " + getContentType() + System.lineSeparator()).getBytes());
        if(getContentLength() > 0)
        	os.write(("Content-Length: " + getContentLength() + System.lineSeparator() + System.lineSeparator()).getBytes());
        if(getResponseBody() != null)
        	os.write(getResponseBodyBytes());
        os.flush();
        socket.close();
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

    public byte[] getResponseBodyBytes() {
		return responseBodyBytes;
	}

	public void setResponseBodyBytes(byte[] responseBodyBytes) {
		this.responseBodyBytes = responseBodyBytes;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int length) {
		this.contentLength = length;
	}

	public void sendHeader(String key, String value) throws IOException {
        if (!responseLineSent)
            sendResponseLine(statusCode);

        out.println(key + ": " + value);
        out.flush();
    }

	public void setContentRange(int[] rangeArray) {
		this.contentRange = rangeArray;
	}
	
	public int[] getContentRange() {
		return this.contentRange;
	}
}

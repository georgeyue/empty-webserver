package com.swift;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

public class Response {
    private Socket socket;
    private int statusCode;
    private String contentType;
	private byte[] responseBody;
	private int contentLength;
    private PrintWriter out;
    private Header header;
    
    private OutputStream os;

    private boolean responseLineSent = false;
	private int[] contentRange;

    public Response(Socket socket) throws IOException {
        header = new Header();
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

        String resLine = "";

        switch(statusCode) {
            case 404:
                resLine = "HTTP/1.1 404 Not Found";
                break;
            case 405:
            	resLine = "HTTP/1.1 405 Method Not Allowed";
            	break;
            case 401:
            	resLine ="HTTP/1.1 401 Error";
            	break;
            case 302:
                out.println("HTTP/1.1 302 Found");
                break;
            case 200:
            	resLine ="HTTP/1.1 200 OK";
            	break;
            case 206:
            	resLine = "HTTP/1.1 206 Partial Content";
            	break;
            default:
                resLine ="HTTP/1.1 200 OK";
              
        }
        out.println(resLine);
        Server.sout(resLine);
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
            setHeader("Content-Type", getContentType());
        if(getContentLength() > 0)
        	setHeader("Content-Length", Integer.toString(getContentLength()));
        if(getContentRange() != null) {
        	setHeader("Content-Range", "bytes " + getContentRange()[0] + "-" + getContentRange()[1] + "/" + getContentLength());
        }
        header.writeTo(out);
        if(getResponseBody() != null)
        	out.print(String.format("%n") + getResponseBody());
        out.flush();
        Server.sout("");
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
		this.responseBody = body.getBytes(Charset.defaultCharset());
	}

	public String getResponseBody() {
		if (this.responseBody != null)
			return new String(this.responseBody);
		else
			return null;
	}

    public byte[] getResponseBodyBytes() {
		return responseBody;
	}

	public void setResponseBodyBytes(byte[] responseBodyBytes) {
		this.responseBody = responseBodyBytes;
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

    public String getHeader(String key) {
        return header.get(key);
    }

    public void setHeader(String key, String value) {
        header.put(key, value);
    }
	public void setContentRange(int[] rangeArray) {
		this.contentRange = rangeArray;
	}
	
	public int[] getContentRange() {
		return this.contentRange;
	}
}

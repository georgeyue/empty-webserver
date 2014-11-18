package com.swift;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Request {
    private Response response;
    private Socket socket;
    private String method;
    private String url;
    private String protocol;
    private String requestLine;
    private String[] tokenizedRequestLine;
    private String username;
    private String password;

    public Request(Socket socket) throws IOException {
        this.socket = socket;
        this.response = new Response(socket);
        method = null;
        url = null;
        protocol = null;
        requestLine = null;
    }

    public String getRequestLine() {
        BufferedReader in;
        if (requestLine == null) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                requestLine = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return requestLine;
    }

    public String getProtocol() {
        if (protocol == null) {
            String[] tokens = getTokenizedRequestLine();
            protocol = tokens.length == 3 ? tokens[2] : "";
        }
        return protocol;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getMethod() {
        String[] tokens;

        if (method == null) {
            tokens = getTokenizedRequestLine();
            if(tokens.length == 3) {
                method = tokens[0];
            } else {
                method = "";
                // log somekind of warn message if requestLine no method
            }
        }
        return method;
    }

    private String[] getTokenizedRequestLine() {
        if (tokenizedRequestLine == null) {
            String line = getRequestLine();
            tokenizedRequestLine = line.split(" ");
        }
        return tokenizedRequestLine;
    }

    public Response getResponse() {
        return response;
    }

    public String getUrl() {
        if (url == null) {
            String[] tokens = getTokenizedRequestLine();
            url = tokens.length == 3 ? tokens[1] : "";
        }
        return url;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
    
}
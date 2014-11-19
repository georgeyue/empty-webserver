package com.swift;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.net.URLDecoder;
import java.util.*;

public class Request {
    private Response response;
    private Socket socket;
    protected String method;
    protected String url;
    private String protocol;
    protected String requestLine;
    private String[] tokenizedRequestLine;
    private String username;
    private String password;
    protected Map<String, String> queryParams;
    protected String rawQueryParams;
    private String pathname;
    private Map<String, String> header;
    private String body;

    public void setUrl(String url) {
        this.url = url;
    }

    public Request(Socket socket) throws IOException {
        this.socket = socket;
        this.response = new Response(socket);
        method = null;
        url = null;
        protocol = null;
        requestLine = null;
        body = null;
    }

    public String getRequestLine() {
        if (requestLine == null)
            parseRequest();
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
            try {
                url = tokens.length > 1 ? URLDecoder.decode(tokens[1], "UTF-8") : "";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
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

	public void setHeader(String name, String value ){
		if (header == null)
			header = new HashMap<String, String>();
		header.put(name.toLowerCase(), value);
	}
	
    //Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==

    public String getPathname() {
        getQueryParams();
        return pathname;
    }

    public Map<String, String> getQueryParams() {
        if (queryParams == null) {
            queryParams = new HashMap<String, String>();
            String[] tokens = getTokenizedRequestLine();
            if (tokens.length > 1) {
                String[] tmp = tokens[1].split("\\?");
                pathname = tmp.length > 0 ? decode(tmp[0]) : "";
                rawQueryParams = tmp.length > 1 ? tmp[1] : "";

                String[] pairs = rawQueryParams.split("&");
                for(String pair : pairs) {
                    String[] kv = pair.split("=");
                    if (kv.length > 0) {
                        String v = kv.length > 1 ? decode(kv[1]) : "";
                        queryParams.put(decode(kv[0]), v);
                    }
                }
            }
        }
        return queryParams;
    }


    public String decode(String str) {
        String ret;
        try {
            ret = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }


    public void parseRequest() {
        Scanner in;
        String line;
        int length;
        if (header == null)
            header = new HashMap<String, String>();

        try {
            in = new Scanner(socket.getInputStream());

            requestLine = in.nextLine();
            if (requestLine == null)
                requestLine = "";

            while(in.hasNextLine() && (line = in.nextLine()) != null && !line.isEmpty()) {
                parseRequestHeader(line);
            }

            String cll = getHeader("content-length");
            length = (cll == null ? 0 : Integer.parseInt(cll));
            if (length > 0) {
                body = in.findWithinHorizon(".*", length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHeader(String key) {
        if (header == null)
            parseRequest();
        return header.get(key.toLowerCase());
    }

    public void parseRequestHeader(String line) {
        if (header == null) header = new HashMap<String, String>();

        String[] tokens = line.split(":\\s*");
        if (tokens.length == 2) {
            header.put(tokens[0].toLowerCase(), tokens[1]);
        }
    }

    public String getBody() {
        if (body == null)
            parseRequest();
        return body;
    }
}


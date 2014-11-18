package com.swift;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private Response response;
    private Socket socket;
    protected String method;
    protected String url;
    private String protocol;
    protected String requestLine;
    private String[] tokenizedRequestLine;
    protected Map<String, String> queryParams;
    private String rawQueryParams;

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
            try {
                url = tokens.length == 3 ? URLDecoder.decode(tokens[1], "UTF-8") : "";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return url;
    }

    private String getPathname() {
        return "";
    }


    public Map<String, String> getQueryParams() {
        if (queryParams == null) {
            queryParams = new HashMap<String, String>();
            String[] tokens = getTokenizedRequestLine();
            if (tokens.length >= 3) {
                rawQueryParams = tokens[1].split("\\?").length > 1
                        ? tokens[1].split("\\?")[1]
                        : "";

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
}

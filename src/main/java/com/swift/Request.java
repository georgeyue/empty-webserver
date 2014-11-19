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
    private String pathname;
    private Map<String, String> header;

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
        BufferedReader in = null;
        String LF = System.getProperty("line.separator");
        String line = null;
        boolean hasCRLF = false;
        if (header == null)
            header = new HashMap<String, String>();

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while((line = in.readLine()) != null) {
                if (requestLine == null)
                    requestLine = line;
                else {
                    if (line.equals(LF)) {
                        hasCRLF = true;
                        continue;
                    }

                    if (hasCRLF) {
                    } else {
                        parseRequestHeader(line);
                    }
                }
                if (line.isEmpty()) break;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
//        } finally {
//            if (in != null) try {
//                in.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    public String getHeader(String key) {
        if (header == null)
            parseRequest();
        return header.get(key.toLowerCase());
    }

    public void parseRequestHeader(String line) {
        if (header == null) header = new HashMap<String, String>();

        String[] tokens = line.split(": ");
        if (tokens.length == 2) {
            header.put(tokens[0].toLowerCase(), tokens[1]);
        }
    }
}

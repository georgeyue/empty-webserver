package com.swift;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
public class RequestTest {


    @Test
    public void initWithSocket() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET / HTTP");
        Request req = new Request(socket);
        assertNotNull(req.getSocket());
    }

    @Test
    public void getRequestMethod() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET / HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("GET", req.getMethod());
    }

    @Test
    public void getRequestMethodWhenNotProvided() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("");

        Request req = new Request(socket);
        assertEquals("", req.getMethod());
    }

    @Test
    public void getRequestUrl() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("/foobar", req.getUrl());
    }

    @Test
    public void returnsRequestProtocol() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /foobar HTTP/1.1");

        Request req = new Request(socket);
        assertEquals("HTTP/1.1", req.getProtocol());
    }

    @Test
    public void shouldGetPathname() throws IOException {
        String line = "GET /apple?name=bean%20stalk HTTP/1.1";
        FakeRequest req = new FakeRequest();
        req.setRequestLine(line);
        assertEquals("/apple", req.getPathname());
    }

    @Test
    public void returnRequestLine() throws IOException {
        String line = "GET / HTTP";
        FakeSocket socket = new FakeSocket();
        socket.setText(line);
        Request req = new Request(socket);

        assertEquals(line, req.getRequestLine());
    }

    @Test
    public void shouldParsePathname() throws IOException {
        String line = "GET /apple?name=bean%20stalk HTTP/1.1";
        FakeRequest req = new FakeRequest();
        req.setRequestLine(line);
        assertEquals("/apple?name=bean stalk", req.getUrl());
    }

    @Test
    public void shouldDecodeString() throws IOException {
        FakeRequest req = new FakeRequest();
        assertEquals(" ", req.decode("%20"));
    }

    @Test
    public void parseRequestHeader() throws IOException {
        String line = "key: value";
        FakeRequest request = new FakeRequest();
        request.parseRequestHeader(line);
        assertEquals("value", request.getHeader("key"));
    }
    
    @Test
	public void canGetAllRequestHeaders() throws Exception {
        String line = "key: value";
        FakeRequest request = new FakeRequest();
        request.parseRequestHeader(line);
        assertEquals("{key=value}", request.getRequestHeaders().toString());
	}

    @Test
    public void parseRequestForRequestLine() throws IOException {
        String LS = System.getProperty("line.separator");
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /apples HTTP/1.1" + LS
                        + "Location: http://localhost" + LS
//                        + LS
//                        + "Some body stuff\r\n"
        );
        FakeRequest req = new FakeRequest(socket);

        assertEquals("/apples", req.getPathname());
        assertEquals("GET", req.getMethod());
        assertEquals("GET /apples HTTP/1.1", req.getRequestLine());
    }

    @Test
    public void parseForRequestLineWithNoHeaderAndBody() throws IOException {
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /apples HTTP/1.1");
        FakeRequest req = new FakeRequest(socket);

        req.parseRequest();
        assertEquals("GET", req.getMethod());
        assertEquals("GET /apples HTTP/1.1", req.getRequestLine());
    }

    @Test
    public void parseForHeader() throws IOException {
        String LS = System.getProperty("line.separator");
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /apples HTTP/1.1" + LS
                        + "Location: http://localhost" + LS
                        + "Accept: */*" + LS
                        + LS
//                        + "Some body stuff\r\n"
        );
        FakeRequest request = new FakeRequest(socket);
        assertEquals("*/*", request.getHeader("accept"));
    }

    @Test
    public void parseRequestForBody() throws Exception {
        String LS = System.getProperty("line.separator");
        FakeSocket socket = new FakeSocket();
        socket.setText("GET /apples HTTP/1.1" + LS
                        + "Location: http://localhost" + LS
                        + "Accept: */*" + LS
                        + "Content-length: 15" + LS
                        + LS
                        + "Some body stuff\r\n"
        );
        FakeRequest request = new FakeRequest(socket);

//        BufferedReader in = new BufferedReader(new InputStreamReader(getInputStream()));
//        String line;
//        int c;
//        while((c = in.read()) != -1) {
//            System.out.println((char)c);
//        }
//        while((line = in.readLine()) != null) {
//            System.out.println(line);
//        }

        assertEquals("Some body stuff", request.getBody());
    }


   @Test
    public void shouldExtractQueryParam() throws IOException {
        String line = "GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP";
        FakeRequest req = new FakeRequest();
        req.setRequestLine(line);

        Map<String, String> qs = req.getQueryParams();
        assertEquals( "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", qs.get("variable_1"));
        assertEquals( "stuff", qs.get("variable_2") );
    }
}
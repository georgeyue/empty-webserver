package com.swift;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FakeSocketTest {

    FakeSocket socket;

    @Before
    public void setUp() {
        socket = new FakeSocket();
        socket.setText("GET / HTTP/1.1");
    }

    @Test
    public void testGetInputStream() throws Exception {
        InputStream is = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());

        BufferedReader r1 = new BufferedReader(new InputStreamReader(is));
        BufferedReader r2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        assertEquals(r1.readLine(), r2.readLine());
    }

    @Test
    public void verifyOutputStreamData() throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("apples");
        out.flush();
        assertEquals(socket.getText(), String.format("apples%n"));
    }
}
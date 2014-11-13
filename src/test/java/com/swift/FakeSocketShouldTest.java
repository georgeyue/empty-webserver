package com.swift;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FakeSocketShouldTest {

    FakeSocket socket;

    @Before
    public void setUp() {
        socket = new FakeSocket();
        socket.setText("GET / HTTP/1.1");
    }

    @Test
    public void getInputStream() throws Exception {
        InputStream is = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());

        BufferedReader r1 = new BufferedReader(new InputStreamReader(is));
        BufferedReader r2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        assertEquals(r1.readLine(), r2.readLine());
    }

    @Test
    public void outputStreamData() throws IOException {
        socket.clearOutput();
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("apples");
        out.flush();
        assertEquals(socket.getText(), String.format("apples%n"));
    }
}
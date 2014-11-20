package com.swift;

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestServerShould {

	int testPort = 5000;
	String testDir = "/";
	SwiftServer mySwiftServer;

    @Before
	public void setUp() throws IOException {
        mySwiftServer = new SwiftServer(testPort, testDir);
        new Thread() {
            public void run() {
                try {
                    mySwiftServer.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

	@After
	public void tearDown() throws IOException {
        if (mySwiftServer != null)
            mySwiftServer.stop();
	}

    @Test
    public void serverStarts() {
        assertFalse(mySwiftServer.isStopped());
    }

    @Test
    public void serverStops() throws IOException {
        mySwiftServer.stop();
        assertTrue(mySwiftServer.isStopped());
    }

	@Test
	public void listenOnPort5000() throws IOException {
        Socket testClient = new Socket(InetAddress.getLocalHost(), testPort);

        SocketAddress clientAddress = testClient.getRemoteSocketAddress();
        int myPort = ((InetSocketAddress) clientAddress).getPort();

        assertEquals(testPort, myPort);
    }

    @Test
    public void return404() throws IOException {
        Socket testClient = new Socket(InetAddress.getLocalHost(), testPort);
        String inputStr;

        PrintWriter pw = new PrintWriter(testClient.getOutputStream(), true);
        pw.println("GET /foobar HTTP/1.1\r\n");

        BufferedReader input = new BufferedReader(new InputStreamReader(testClient.getInputStream()));
        inputStr = input.readLine();

        assertEquals("HTTP/1.1 404 Not Found", inputStr);
    }

    @Test
	public void shouldBeAbleToReturnASocket() throws Exception {
		assertNotNull(mySwiftServer.getSocket());
	}
    
    @Test
    public void shouldSetDirectory() {
        String dir = "/a/b/c/d/e";
        mySwiftServer.setDirectory(dir);
        assertEquals(SwiftServer.getDirectory(), dir);
    }
}

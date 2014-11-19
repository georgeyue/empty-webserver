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
	Server myServer;

    @Before
	public void setUp() throws IOException {
        myServer = new Server(testPort, testDir);
        new Thread() {
            public void run() {
                try {
                    myServer.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

	@After
	public void tearDown() throws IOException {
        if (myServer != null)
            myServer.stop();
	}

    @Test
    public void serverStarts() {
        assertFalse(myServer.isStopped());
    }

    @Test
    public void serverStops() throws IOException {
        myServer.stop();
        assertTrue(myServer.isStopped());
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
		assertNotNull(myServer.getSocket());
	}
    
    @Test
    public void shouldSetDirectory() {
        String dir = "/a/b/c/d/e";
        myServer.setDirectory(dir);
        assertEquals(Server.getDirectory(), dir);
    }
}

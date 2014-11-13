package com.swift;

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.swift.Server;


public class HTTPServerShouldTest {

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
	public void itIslisteningOnPort5000() throws IOException {
        Socket testClient = new Socket(InetAddress.getLocalHost(), testPort);

        SocketAddress clientAddress = testClient.getRemoteSocketAddress();
        int myPort = ((InetSocketAddress) clientAddress).getPort();

        assertEquals(testPort, myPort);
    }

    @Test
    public void itReturns404() throws IOException {
        Socket testClient = new Socket(InetAddress.getLocalHost(), testPort);
        String inputStr = "";

        PrintWriter pw = new PrintWriter(testClient.getOutputStream());
        pw.println("GET /foobar HTTP/1.1");
        pw.flush();

        BufferedReader input = new BufferedReader(new InputStreamReader(testClient.getInputStream()));
        inputStr = input.readLine();

        assertEquals("HTTP/1.1 404 Not Found", inputStr);
    }


    @Test
    public void setDirectory() {
        String dir = "/a/b/c/d/e";
        myServer.setDirectory(dir);
        assertEquals(myServer.getDirectory(), dir);
    }
}

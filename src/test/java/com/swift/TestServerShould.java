package com.swift;

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.swift.Server;


public class TestServerShould {

	int testPort = 5000;
	Server myServer;

    @Before
	public void setUp() throws IOException {
        myServer = new Server(testPort);
        new Thread() {
            public void run() {
                try {
                    myServer.run();
                } catch (IOException e) {
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
        assertFalse(myServer.isClosed());
    }

    @Test
    public void serverStops() throws IOException {
        myServer.stop();
        assertTrue(myServer.isClosed());
    }

	@Test
	public void listenOnPort5000() throws IOException {
        Socket testClient = new Socket(InetAddress.getLocalHost(), testPort);

        SocketAddress clientAddress = testClient.getRemoteSocketAddress();
        int myPort = ((InetSocketAddress) clientAddress).getPort();

        assertEquals(testPort, myPort);
        assertEquals(testPort, myServer.getSocket().getLocalPort());
    }

    @Test
    public void return404() throws IOException {
        Socket testClient = new Socket(InetAddress.getLocalHost(), testPort);
        String inputStr = "";

        PrintWriter pw = new PrintWriter(testClient.getOutputStream());
        pw.println("GET / HTTP/1.1");
        pw.flush();

        BufferedReader input = new BufferedReader(new InputStreamReader(testClient.getInputStream()));
        inputStr = input.readLine();


        assertEquals("HTTP/1.1 404 Not Found", inputStr);
    }
}

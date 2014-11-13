package com.swift;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpServerSocketShouldTest {
	private int testPort = 5000;
	private httpServerSocket mySocket;

    @Before
	public void setUp() throws IOException {
        mySocket = new httpServerSocket(testPort);
        new Thread() {
            public void run() {
                try {
                    mySocket.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

	@After
	public void tearDown() throws IOException {
        if (mySocket != null)
            mySocket.close();
	}
	
	@Test
	public void socketOpenedOnPort5000() throws UnknownHostException, IOException {
        Socket testClient = new Socket(InetAddress.getLocalHost(), testPort);

        SocketAddress clientAddress = testClient.getRemoteSocketAddress();
        int myPort = ((InetSocketAddress) clientAddress).getPort();

        assertEquals(testPort, myPort);
        assertEquals(testPort, mySocket.getSocket().getLocalPort());
        
        testClient.close();
	}

	@Test
	public void socketHasClosed() throws Exception {
		assertFalse(mySocket.isClosed());
		mySocket.close();
		assertTrue(mySocket.isClosed());
	}
}

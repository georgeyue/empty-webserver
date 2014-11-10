package com.swift;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.swift.Server;


public class ServerShould {

	int testPort;
	Server myServer;
	
	@Before
	public void setUp() {
		testPort = 5000;
		try {
			myServer = new Server(testPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		myServer.close();
	}
	
	@Test
	public void listenOnPort5000() {
		try {
			Socket testClient = new Socket("localhost",testPort);
			SocketAddress clientAddress = testClient.getRemoteSocketAddress();
			int myPort = ((InetSocketAddress) clientAddress).getPort();
			testClient.close();
			assertEquals(5000, myPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldReturn404() throws Exception {
		try {
			Socket testClient = new Socket("localhost", testPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

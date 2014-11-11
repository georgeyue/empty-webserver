package com.swift;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class httpServerSocket {

	private ServerSocket serverSocket;
    private int portNumber;
	
	public httpServerSocket(int testPort) throws IOException {
		this.portNumber = testPort;
		serverSocket = new ServerSocket(testPort);
	}

	public ServerSocket getSocket() {
		return this.serverSocket;
	}

	public void run() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.serverSocket.close();
	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.serverSocket.isClosed();
	}

}

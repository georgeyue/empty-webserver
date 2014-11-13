package com.swift;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class httpServerSocket implements SwiftServerSocket {

	private ServerSocket serverSocket;
	
	public httpServerSocket(int testPort) throws IOException {
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

	public Socket accept() throws IOException {
		// TODO Auto-generated method stub
		return this.serverSocket.accept();
	}

}

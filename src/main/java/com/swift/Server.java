package com.swift;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private ServerSocket serverSocket;
	
	public Server(int portNumber) throws IOException {
		serverSocket = new ServerSocket(portNumber);
	}
	
    public static void main(String[] args) {
    	int port = Integer.parseInt(args[0]);
    	try {
			Server myServer = new Server(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void close() {
		// TODO Auto-generated method stub
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

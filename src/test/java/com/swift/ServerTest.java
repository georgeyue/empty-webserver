package com.swift;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ServerTest {
    int port = 5000;
    String directory = "";
    FakeServerSocket serverSocket;

    @Before
    public void setUp() {
        serverSocket = new FakeServerSocket();
    }

    @Test
    public void ItDoesNothingWhenTheSocketServerIsClosed() throws IOException {
        serverSocket.close();

        Server server = new Server(port, directory, serverSocket);
        server.run();
    }

    @Test
    public void ItDoesNothingWhenTheServerIsStopped() throws IOException {
        serverSocket.open();

        Server server = new Server(port, directory, serverSocket);
        server.stop();
        server.run();
    }

    @Test
    public void ItAcceptsAConnectionWhenTheServerIsRunning() throws IOException {
        serverSocket.open();
        serverSocket.setNumberOfConnections(1);

        FakeSocket socket = new FakeSocket();
        serverSocket.setSocket(socket);

        FakeRequestHandler fakeRequestHandler = new FakeRequestHandler();

        Server server = new Server(port, directory, serverSocket);
        server.setRequestHandler(fakeRequestHandler);
        server.run();

        assertSame(socket, fakeRequestHandler.handledSocket);
    }
}

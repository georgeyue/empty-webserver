package com.swift;

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void ItDoesNothingWhenStopped() throws IOException {
        int port = 5000;
        String directory = "";
        FakeServerSocket socket = new FakeServerSocket();
        socket.close();

        Server server = new Server(port, directory, socket);
        server.run();
    }

    @Test
    public void ItDoesNothingWhenTheServerIsStopped() throws IOException {
        int port = 5000;
        String directory = "";
        FakeServerSocket socket = new FakeServerSocket();
        socket.open();

        Server server = new Server(port, directory, socket);
        server.stop();
        server.run();
    }

    @Test
    public void ItAcceptsAConnectionWhenTheServerIsRunning() throws IOException {
        int port = 5000;
        String directory = "";
        FakeServerSocket serverSocket = new FakeServerSocket();
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

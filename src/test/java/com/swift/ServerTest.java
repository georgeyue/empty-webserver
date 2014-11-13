package com.swift;
import org.junit.Test;

import java.io.IOException;

public class ServerTest {

    @Test
    public void ItDoesNothingWhenStopped() throws IOException {
        int port = 5000;
        String directory = "";
        FakeServerSocket socket = new FakeServerSocket();
        socket.close();

        Server server = new Server(port, directory, socket);
    }
}

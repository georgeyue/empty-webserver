package com.swift;

import java.io.IOException;

public class Server {

    public static void main(String[] args) {
        SwiftServer swiftServer = null;
        try {
            swiftServer = new SwiftServer(Integer.parseInt(args[1]), args[3]);
            swiftServer.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

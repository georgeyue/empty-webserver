package com.swift;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by gyue on 11/11/14.
 */
public class FakeSocket extends Socket {
    @Override
    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
    }
}

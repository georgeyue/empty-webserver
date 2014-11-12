package com.swift;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by gyue on 11/11/14.
 */
public class FakeSocket extends Socket {
    private String requestHeader = "GET / HTTP/1.1";

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(requestHeader.getBytes());
    }

    public void setRequestHeader(String s) {
        requestHeader = s;
    }
}

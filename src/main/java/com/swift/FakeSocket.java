package com.swift;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FakeSocket extends Socket {
    private StringBuilder text = new StringBuilder("a a a");

    @Override
    public OutputStream getOutputStream() throws IOException {
        text.setLength(0);
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                text.append((char) b);
            }
        };
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(text.toString().getBytes());
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String s) {
        text.setLength(0);
        text.append(s);
    }
}

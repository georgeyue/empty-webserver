package com.swift;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FakeSocket extends Socket {
    private StringBuilder text = new StringBuilder("");
    private OutputStream out;

    public FakeSocket() {
        out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                text.append((char) b);
            }
        };
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(text.toString().getBytes());
    }

    public void clearOutput() {
        text.setLength(0);
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String s) {
        text.setLength(0);
        text.append(s);
        text.append(String.format("%n"));
    }
}

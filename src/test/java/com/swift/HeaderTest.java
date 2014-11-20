package com.swift;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static org.junit.Assert.*;

public class HeaderTest {
    @Test
    public void setHeader() {
        Header header = new Header();
        header.put("key", "value");
        assertEquals("value", header.get("key"));
    }

    @Test
    public void shouldWriteToSocket() throws IOException {
        FakeSocket socket = new FakeSocket();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        Header header = new Header();
        header.put("key", "value");
        header.put("one", "two");
        header.writeTo(pw);

        assertEquals("one: two\r\nkey: value\r\n", socket.getText());
    }

}
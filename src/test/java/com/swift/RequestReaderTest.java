package com.swift;

import junit.extensions.TestSetup;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class RequestReaderTest {

    public RequestReader reader;

    @Before
    public void setup() {
        reader = new RequestReader();
    }

    @Test
    public void returnsAStringFrom() {
        InputStream ins = new ByteArrayInputStream("banana".getBytes());
        String result = reader.read(ins);
        assertEquals("banana", result);
    }

    @Test
    public void returnStringWithNewLine() {
        InputStream ins = new ByteArrayInputStream(("banana\n" + "bomb\r\n").getBytes());
        String result = reader.read(ins);
        assertEquals("banana\nbomb\r\n", result);
    }
}
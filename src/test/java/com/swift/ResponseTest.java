package com.swift;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void shouldSetHeaderNotFound() throws Exception {
        Response response = new Response(new FakeSocket());
        response.setNotFoundHeader();
        response.send();

        assertEquals(404, response.getStatusCode());
    }
}
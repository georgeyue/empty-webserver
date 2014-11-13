package com.swift;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StaticRouteTest {


    @Test
    public void createNewRoute() throws IOException {
        Route route = new StaticRoute(new FakeRequest());
        assertNotNull(route);
    }

    @Test
    public void testsForMatch() throws IOException {
        FakeRequest req = new FakeRequest();
        req.setUrl("/foobar");

        Route route = new StaticRoute(new FakeRequest());

        assertTrue(route.isMatch());
    }
}
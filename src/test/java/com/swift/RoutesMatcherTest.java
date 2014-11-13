package com.swift;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoutesMatcherTest {
    @Test
    public void shouldAddRoutes() {
        RoutesMatcher matcher = new RoutesMatcher();
        matcher.add(new Route());

        assertEquals(1, matcher.getRoutesCount());
    }

}
package com.swift.route;

import com.swift.router.StaticRoute;
import com.swift.router.RoutesMatcher;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoutesMatcherTest {
    @Test
    public void shouldAddRoutes() {
        RoutesMatcher matcher = new RoutesMatcher();
        matcher.add(new StaticRoute());

        assertEquals(1, matcher.getRoutesCount());
    }

}
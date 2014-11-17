package com.swift.route;

import com.swift.FakeRequest;
import com.swift.router.StaticRoute;
import com.swift.router.RoutesMatcher;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RoutesMatcherTest {
    @Test
    public void shouldAddRoutes() throws IOException {
        RoutesMatcher matcher = new RoutesMatcher();
        matcher.add(new StaticRoute( new FakeRequest() ));

        assertEquals(1, matcher.getRoutesCount());
    }

}
package com.swift.route;

import com.swift.FakeRequest;
import com.swift.router.Route;
import com.swift.router.StaticRoute;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StaticRouteTest {


    @Test
    public void createNewRoute() throws IOException {
        Route route = new StaticRoute();
        assertNotNull(route);
    }

    @Test
    public void testsForMatch() throws IOException {
        FakeRequest req = new FakeRequest();
        req.setUrl("/foobar");

        Route route = new StaticRoute();

        assertTrue(route.isMatch(req));
    }

}
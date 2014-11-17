package com.swift.route;

import com.swift.FakeRequest;
import com.swift.router.RoutesMatcher;
import com.swift.router.StaticRoute;
import com.swift.router.SwiftRoutesMatcher;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SwiftRoutesMatcherTest {
    @Test
    public void shouldAddRoutes() throws IOException {
        RoutesMatcher matcher = new SwiftRoutesMatcher();
        assertEquals(0, matcher.getRoutesCount());
        matcher.add(new StaticRoute());
        assertEquals(1, matcher.getRoutesCount());
    }

    @Test
    public void addTwoRoutes() {
        RoutesMatcher matcher = new SwiftRoutesMatcher();
        matcher.add(new StaticRoute());
        matcher.add(new StaticRoute());
        assertEquals(2, matcher.getRoutesCount());
    }

    @Test
    public void isProcessesMatchedRoute() throws IOException {
        FakeRequest request = new FakeRequest();
        request.setUrl("/banana");
        TestRoute testroute = new TestRoute("^/banana$");
        RoutesMatcher matcher = new SwiftRoutesMatcher();
        matcher.add(testroute);

        matcher.processRequest(request);
        assertTrue(testroute.isRun());
    }

    @Test
    public void itDoesNotProcessMisMatchedRoute() throws IOException {
        FakeRequest request = new FakeRequest();
        request.setUrl("/banana");
        TestRoute testroute = new TestRoute("^/nothingness$");
        RoutesMatcher matcher = new SwiftRoutesMatcher();
        matcher.add(testroute);

        matcher.processRequest(request);
        assertFalse(testroute.isRun());
    }

    @Test
    public void shouldIndicateRequestIsProcessed() throws IOException {
        FakeRequest request = new FakeRequest();
        request.setUrl("/banana");
        TestRoute testroute = new TestRoute("^/banana$");
        RoutesMatcher matcher = new SwiftRoutesMatcher();
        matcher.add(testroute);

        matcher.processRequest(request);
        assertTrue(matcher.requestIsProccessed());
    }

    @Test
    public void itConstructsRoutes() {
        RoutesMatcher matcher = new SwiftRoutesMatcher();
        matcher.constructRoutes();
        assertEquals(3, matcher.getRoutesCount());
    }
}
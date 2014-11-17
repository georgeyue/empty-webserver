package com.swift.router;

import com.swift.Request;

import java.util.ArrayList;
import java.util.List;

public class RoutesMatcher {
    public List<Route> routes = new ArrayList<Route>();

    public void add(Route route) {
        routes.add(route);
    }

    public int getRoutesCount() {
        return routes.size();
    }

    public void processRequest(Request request) {
        for (Route route : routes) {
            if (route.isMatch(request)) {
                route.handle();
                break;
            }
        }
    }
}

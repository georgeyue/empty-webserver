package com.swift;

import java.util.ArrayList;
import java.util.List;

public class RoutesMatcher {
    public List<Route> routes = new ArrayList<Route>();

    public void add(StaticRoute route) {
        routes.add(route);
    }

    public int getRoutesCount() {
        return routes.size();
    }
}

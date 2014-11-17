package com.swift.router;

import com.swift.Request;
import com.swift.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class RoutesMatcher {
    public List<Route> routes = new ArrayList<Route>();
    private boolean requestIsProccessed;

    public void add(Route route) {
        routes.add(route);
    }

    public int getRoutesCount() {
        return routes.size();
    }

    public void processRequest(Request request) throws IOException {
        for (Route route : routes) {
            if (route.isMatch(request)) {
                requestIsProccessed = true;
                route.handle();
                break;
            }
        }
    }

    public boolean requestIsProccessed() {
        return requestIsProccessed;
    }

    public abstract void constructRoutes();
}

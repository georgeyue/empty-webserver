package com.swift.router;

import com.swift.Request;

public interface Route {
    void handle();
    boolean isMatch(Request req);
}

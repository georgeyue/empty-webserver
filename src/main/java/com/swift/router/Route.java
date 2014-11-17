package com.swift.router;

import com.swift.Request;

import java.io.IOException;

public interface Route {
    void handle() throws IOException;
    boolean isMatch(Request request);
}

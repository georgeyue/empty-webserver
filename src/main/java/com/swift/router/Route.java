package com.swift.router;

public interface Route {
    void handle();
    boolean isMatch();
}

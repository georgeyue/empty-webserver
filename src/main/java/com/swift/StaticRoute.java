package com.swift;

public class StaticRoute implements Route {
    private Request request;

    public StaticRoute(Request request) {
        this.request = request;
    }

    public StaticRoute() {

    }

    @Override
    public void handle() {

    }

    @Override
    public boolean isMatch() {
        return true;
    }
}

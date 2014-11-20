package com.swift;

import java.io.PrintWriter;
import java.util.HashMap;

public class Header extends HashMap<String, String> {

    public void writeTo(PrintWriter out) {
        for(String k : this.keySet()) {
            out.write(k + ": " + this.get(k) + "\r\n");
        }
        out.flush();
    }
}

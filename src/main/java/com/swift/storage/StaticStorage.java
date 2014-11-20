package com.swift.storage;

public class StaticStorage {
    public static String data = "";

    public static String get() {
        return data;
    }

    public static void set(String str) {
        data = str;
    }

    public static void clear() {
        data = "";
    }
}

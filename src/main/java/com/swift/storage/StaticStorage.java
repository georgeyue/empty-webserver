package com.swift.storage;

public class StaticStorage {
    public static StringBuilder data = new StringBuilder();

    public static String get() {
        return data.toString();
    }

    public static void set(String str) {
        data.append(str);
    }

    public static void clear() {
        data = new StringBuilder();
    }
}

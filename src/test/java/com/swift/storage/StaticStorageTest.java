package com.swift.storage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StaticStorageTest {

    @Before
    public void setup() {
        StaticStorage.clear();
    }

    @Test
    public void testGetSet() throws Exception {
        StaticStorage.set("banan");
        assertEquals("banan", StaticStorage.get());
    }

    @Test
    public void testClear() throws Exception {
        assertTrue(StaticStorage.get().isEmpty());
        StaticStorage.set("banan");
        assertFalse(StaticStorage.get().isEmpty());
        StaticStorage.clear();
        assertTrue(StaticStorage.get().isEmpty());
    }
}
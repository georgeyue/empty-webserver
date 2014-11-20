package com.swift.router;

import com.swift.FakeRequest;
import com.swift.storage.StaticStorage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FormRouteTest {
    FakeRequest request;
    FormRoute route;

    @Before
    public void setup() throws IOException {
        request = new FakeRequest();
        route = new FormRoute();
        StaticStorage.clear();
    }

    @Test
    public void matchRequest() {
        request.setRequestLine("GET /form HTTP");
        request.setUrl("/form");

        assertTrue(route.isMatch(request));
    }


    @Test
    public void storeStuffIntoStaticWithPostToForm() throws IOException {
        request.setMethod("POST");
        request.setUrl("/form");
        request.setBody("some data");

        route.isMatch(request);
        route.handle();

        assertEquals("some data", StaticStorage.get());
    }
}
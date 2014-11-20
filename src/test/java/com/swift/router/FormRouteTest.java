package com.swift.router;

import com.swift.FakeRequest;
import com.swift.Request;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FormRouteTest {
    @Test
    public void matchRequest() throws IOException {
        FakeRequest request = new FakeRequest();
        request.setRequestLine("GET /form, HTTP");
        request.setUrl("/form");

        FormRoute route = new FormRoute();
        assertTrue(route.isMatch(request));
    }
}
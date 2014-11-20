package com.swift.route;

import com.swift.FakeRequest;
import com.swift.FakeSocket;
import com.swift.Response;
import com.swift.router.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AuthenticateRouteTest {
    public AuthenticateRoute route;
    public FakeRequest request;
    public FakeSocket socket;

    @Before
    public void setUp() throws IOException {
        socket = new FakeSocket();
        route = new AuthenticateRoute();
        request = new FakeRequest(socket);

    }

    @Test
    public void createAAuthenticateRoute() throws IOException {
        Route route = new AuthenticateRoute();
        FakeRequest request = new FakeRequest();
        request.setRequestLine("GET /logs HTTP/1.1");

        assertTrue(route.isMatch(request));
    }

    @Test
    public void shouldNotHandleIllegaRequest() throws IOException {
        Route route = new AuthenticateRoute();
        FakeRequest request = new FakeRequest();
        request.setRequestLine("GET /log HTTP/1.1");

        assertFalse(route.isMatch(request));
    }

    @Test
    public void shouldParseCredentials() {
        request.setRequestLine("GET /log HTTP/1.1");
        byte[] cred = new String("admin:hunter2").getBytes();
        String strCred = Base64.getEncoder().encodeToString(cred);

        request.setHeader("Authorization", "Basic " + strCred);
        HashMap<String, String> result = route.parseCredentials(request);

        assertEquals("admin", result.get("username"));
    }

    @Test
    public void noAuthHEaderPArseCredentialShouldFail() {
        request.setRequestLine("GET /log HTTP/1.1");
        request.setHeader("banan", "nobanana");

        HashMap<String, String> result = route.parseCredentials(request);

        assertNull(result);
    }

    @Test
    public void shouldNotParseCredOnInvalidHeader() {
        request.setRequestLine("GET /log HTTP/1.1");
        byte[] cred = new String("asdf").getBytes();
        String strCred = Base64.getEncoder().encodeToString(cred);

        request.setHeader("Authorization", "Basic " + strCred);
        HashMap<String, String> result = route.parseCredentials(request);

        assertNull(result);
    }

    @Test
    public void shouldAuthAdmin() {
        assertTrue(route.isAdminUser("admin", "hunter2"));
        assertFalse(route.isAdminUser("asdf", "asdf"));
    }

    @Test
    public void handleAuthRequestFailure() throws IOException {
        request.setRequestLine("GET /log HTTP/1.1");
        byte[] cred = new String("asdf").getBytes();
        String strCred = Base64.getEncoder().encodeToString(cred);
        request.setHeader("Authorization", "Basic " + strCred);

        route.isMatch(request);
        route.handle();

        assertEquals(401, request.getResponse().getStatusCode());
        assertEquals("Basic realm=\"Swifter's Lounge\"", request.getResponse().getHeader("WWW-Authenticate"));
    }

    @Test
    public void handleAuthRequestAndSucceed() throws IOException {
        request.setRequestLine("GET /log HTTP/1.1");
        String cred = Base64.getEncoder().encodeToString(new String("admin:hunter2").getBytes());
        request.setHeader("Authorization", "Basic " + cred);

        route.isMatch(request);
        route.handle();

        assertEquals(200, request.getResponse().getStatusCode());
    }
}

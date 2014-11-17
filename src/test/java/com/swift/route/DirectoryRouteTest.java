package com.swift.route;

import static org.junit.Assert.*;

import org.junit.Test;

import com.swift.FakeRequest;
import com.swift.FakeSocket;
import com.swift.Request;
import com.swift.RequestHandler;
import com.swift.router.DirectoryRoute;
import com.swift.router.Route;
import com.swift.router.StaticRoute;

public class DirectoryRouteTest {

	@Test
	public void canListDirectories() throws Exception {
        FakeRequest req = new FakeRequest();
        req.setUrl("/");

        DirectoryRoute route = new DirectoryRoute();

		assertNotNull(route.directoryListing());
	}

}

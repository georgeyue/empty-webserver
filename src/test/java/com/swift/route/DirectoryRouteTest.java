package com.swift.route;

import static org.junit.Assert.*;

import org.junit.Test;

import com.swift.FakeRequest;
import com.swift.router.DirectoryRoute;

public class DirectoryRouteTest {

	@Test
	public void canListDirectories() throws Exception {
        FakeRequest req = new FakeRequest();
        req.setUrl("/");

        DirectoryRoute route = new DirectoryRoute();

		assertNotNull(route.directoryListing());
	}

}

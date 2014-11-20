package com.swift.router;

import com.swift.Request;
import com.swift.storage.StaticStorage;

import java.io.IOException;

public class FormRoute extends BaseRoute {
    @Override
    public void handle() throws IOException {

        if (request.getMethod().equals("POST")) {
            StaticStorage.set(request.getBody());
        }
        response.send(200);
    }

    @Override
    public boolean isMatch(Request request) {
        super.isMatch(request);
        return request.getUrl().equals("/form");
    }
}

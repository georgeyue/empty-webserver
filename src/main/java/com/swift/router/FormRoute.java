package com.swift.router;

import com.swift.Request;
import com.swift.storage.StaticStorage;

import java.io.IOException;

public class FormRoute extends BaseRoute {
    @Override
    public void handle() throws IOException {

        String s = request.getMethod();
        String b = request.getBody();

        if (s.equals("POST") || s.equals("PUT"))
            StaticStorage.set(b);
        if (s.equals("DELETE")) {
            StaticStorage.clear();
        } else {
            response.setResponseBody(StaticStorage.get());
        }
        response.send(200);
    }

    @Override
    public boolean isMatch(Request request) {
        super.isMatch(request);
        return request.getUrl().equals("/form");
    }
}

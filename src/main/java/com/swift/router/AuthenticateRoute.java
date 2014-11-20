package com.swift.router;

import com.swift.Request;
import com.swift.Server;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import javax.xml.bind.DatatypeConverter;

public class AuthenticateRoute extends BaseRoute {

    private static final String adminUsername = "admin";
    private static final String adminPassword = "hunter2";

    @Override
    public void handle() throws IOException {

        HashMap<String, String> creds = parseCredentials(request);

        if (creds != null && isAdminUser(creds.get("username"), creds.get("password"))) {
            StringBuilder sb = new StringBuilder();
            for (String s : Server.logs) {
                sb.append(s + "\n");
            }
            response.setResponseBody(sb.toString());
            Server.logs.clear();
            response.send(200);
        } else {
            response.setHeader("WWW-Authenticate", "Basic realm=\"Swifter's Lounge\"");
            response.setResponseBody("Authentication required");
            response.send(401);
        }
    }

    @Override
    public boolean isMatch(Request request) {
        super.isMatch(request);
        return request.getUrl().equals("/logs")
                && request.getMethod().equals("GET");
    }

    public HashMap<String, String> parseCredentials(Request request) {
        HashMap<String, String> creds = null;
        String ah = request.getHeader("authorization");
        String[] authHeader;
        String cred;
        String[] realCred;

        // Authorization: Basic asdf49jdii034j -> username:password

        if (ah != null) {
            authHeader = ah.split("\\s+");
            if (authHeader.length == 2) {
                cred = new String(Base64.getDecoder().decode((authHeader[1])));
                realCred = cred.split(":");
                if (realCred.length == 2) {
                    creds = new HashMap<String, String>();
                    creds.put("username", realCred[0]);
                    creds.put("password", realCred[1]);
                }
            }
        }

        return creds;
    }

    public boolean isAdminUser(String username, String password){
        if( username.equals(adminUsername) && password.equals(adminPassword)) {
            return true;
        } else {
            return false;
        }
    }
}

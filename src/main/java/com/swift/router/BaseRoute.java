package com.swift.router;

import com.swift.Request;
import com.swift.Response;

public abstract class BaseRoute implements Route {
    protected Request request;
    protected Response response;

    protected BaseRoute() {

    }

    protected BaseRoute(Request request) {
        this.request = request;
        this.response = request.getResponse();
    }

    public boolean isMatch(Request request) {
        this.request = request;
        this.response = request.getResponse();
        return false;
    }
    
    /*protected boolean authenticateUser(){
    	if( "admin".equals(request.getUsername()) && "hunter2".equals(request.getPassword())) {
    		System.out.println("TReading User Name " + request.getUsername());
    		return true;
    	}
    	else {
    		return false;
    	}
    }*/
    
    protected boolean authenticateUser(){
    	try {
	    	String credentials = request.getHeader("Authorization");
	    	String[] userPassword = credentials.split(":");
	    	if( "admin".equals(userPassword[0]) && "hunter2".equals(userPassword[1])) {
	    		System.out.println("TReading User Name " + request.getUsername());
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return false;
    	}
    }
}



package com.alven.server.handler;

import com.alven.server.entity.Request;
import com.alven.server.entity.Response;

public class LoginHandler extends Handler {
    @Override
    public void doGet(Request request, Response response) {
        response.addContent("Sorry, login fail...");
    }

    @Override
    public void doPost(Request request, Response response) {
        response.setType("text/html");
        response.addContent("!DOCTYPE html");
        response.addContent("<html>");
        response.addContent("<head>");
        response.addContent("<title>HOME</title>");
        response.addContent("</head>");
        response.addContent("<body>");
        response.addContent("<h1>Login Successful!</h1><hr>");
        response.addContent("<h2>Welcome back "+request.getParams().get("name")+"</h2>");
        response.addContent("</body>");
        response.addContent("</html>");
    }
}

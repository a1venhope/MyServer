package com.alven.server.handler;

import com.alven.server.entity.Request;
import com.alven.server.entity.Response;

public abstract class Handler {
    public void service(Request request, Response response) {
        if(request.getMethod().equalsIgnoreCase("post")) {
            doPost(request,response);
        } else if (request.getMethod().equalsIgnoreCase("get")) {
            doGet(request,response);
        }
    }
    public abstract void doGet(Request request, Response response);
    public abstract void doPost(Request request, Response response);
}

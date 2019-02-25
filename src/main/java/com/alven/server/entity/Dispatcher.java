package com.alven.server.entity;


import com.alven.server.handler.Handler;
import com.alven.server.web.Context;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 此类负责处理请求，每次请求对应一个新的该类
 */

public class Dispatcher implements Runnable {
    private Context context;
    private Socket client;
    private Request request;
    private Response response;
    private int code;
    private Map<Integer, Filter> preFilters;
    private Map<Integer, Filter> afterFilters;

    public Dispatcher(Context context, Socket clientSocket, Map<Integer, Filter> pre, Map<Integer, Filter> after) {
        this.context = context;
        this.client = clientSocket;
        this.preFilters = pre;
        this.afterFilters = after;
        code = 200;
        try {
            request = new Request(client.getInputStream());
            response = new Response(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            code = 500;
            return;
        }
    }

    public void run() {
        filter(preFilters);
        if(!request.getUrl().equals("/favicon.ico")) {
            System.out.println(request.getRequestInfo()+"\n\n");
        }
        Handler handler = context.getHandler(request.getUrl());
        if(null == handler) {
            code = 404;
        } else {
            handler.service(request,response);
        }
        filter(afterFilters);
        response.responsePush(code,"UTF-8");
        try {
            request.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void filter(Map<Integer,Filter> filters) {
        if (filters == null) {
            return;
        }
        for (int i = 0; i < filters.size(); i++) {
            filters.get(i).opt(request, response);
        }
    }
}

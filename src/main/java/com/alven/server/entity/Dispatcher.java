package com.alven.server.entity;


import com.alven.server.handler.Handler;
import com.alven.server.web.Context;

import java.io.IOException;
import java.net.Socket;

/**
 * 此类负责处理请求，每次请求对应一个新的该类
 */

public class Dispatcher implements Runnable {
    private Context context;
    private Socket client;
    private Request request;
    private Response response;
    private int code;
    private String type;

    public Dispatcher(Context context, Socket clientSocket) {
        this.context = context;
        this.client = clientSocket;
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
        if(!request.getUrl().equals("/favicon.ico")) {
            System.out.println(request.getRequestInfo()+"\n\n");
        }
        Handler handler = context.getHandler(request.getUrl());
        if(null == handler) {
            code = 404;
        } else {
            handler.service(request,response);
            response.responsePush(code,"UTF-8");
        }
        try {
            request.close();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

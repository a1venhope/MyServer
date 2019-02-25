package com.alven.server.entity;

public class LogRequestFilter implements Filter {

    public void opt(Request request, Response response) {
        System.out.println("请求方式：" + request.getMethod());
        System.out.println("请求URL：" + request.getUrl());
        System.out.println("请求参数：" + request.getParams().toString());
    }
}

package com.alven.server.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    public static final String CRLF = "\r\n";
    private String method;
    private String url;
    private Map<String,List<String>> params;
    private InputStream inputStream;
    private String requestInfo;

    public Request() {
        method = "";
        url = "";
        params = new HashMap<String, List<String>>();
    }

    public Request(InputStream is) {
        this();
        int len = 0;
        inputStream = is;
        byte[] bytes = new byte[20480];
        try {
            len = inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestInfo = new String(bytes,0,len);
        parseRequestInfo();
    }

    public void parseRequestInfo() {
        if(null == requestInfo || requestInfo.trim().equals("")) {
            return;
        }
        // 获得请求信息的第一行，包括了 请求方式 HTTP协议；若为 GET 请求，还包括了请求参数
        String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
        int index1 = firstLine.indexOf("/");
        int index2 = firstLine.indexOf("HTTP/");
        method = firstLine.substring(0,index1).trim();
        String str = firstLine.substring(index1,index2).trim();
        if(method.equalsIgnoreCase("get")) {
            // 如若带有参数
            if(str.contains("?")) {
                url = str.substring(0,str.indexOf("?"));
                parseRequestParams(str.substring(str.indexOf("?")+1));
            } else {
                url = str;
            }
        } else if (method.equalsIgnoreCase("post")) {
            url = str;
            parseRequestParams(requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim());
        }
    }

    public void parseRequestParams(String paramString) {
        String[] paramList = paramString.split("&");
        for(String p : paramList) {
            String[] param = p.split("=");
            if(!params.containsKey(param[0].trim())) {
                // 若该参数 name 未加入 map； 初始化
                params.put(param[0],new ArrayList<String>());
            }
            if(param.length > 1) {
                // 将参数 value 加入 map
                params.get(param[0].trim()).add(param[1].trim());
            } else {
                // 当出现参数 value 为空的情况 例：name=&gender=1&age=18
                params.put(param[0].trim(),null);
            }
        }
    }

    /**
     * 仅用于测试 后期注释
     * @param requestInfo 模拟请求信息
     */
    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public void display() {
        System.out.println("Method : "+method);
        System.out.println("URL : "+url);
        System.out.println("Params : ");
        for(String key : params.keySet()) {
            for(String value : params.get(key)) {
                System.out.print(key+" --> "+value+"  ");
            }
            System.out.println();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getParams() {
        return params;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void close() throws IOException {
        inputStream.close();
    }
}

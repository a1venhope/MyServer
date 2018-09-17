package com.alven.server.entity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Response {
    public static final String CRLF = "\r\n";
    private BufferedWriter writer;
    private StringBuilder response;
    private StringBuilder responseContent;
    private int contentLength;
    private String type;

    public Response() {
        response = new StringBuilder();
        responseContent = new StringBuilder();
        contentLength = 0;
    }

    public Response(OutputStream outputStream) {
        this();
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    private void creatResponseHead(int code, String type, String charset) {
        response.append("HTTP/1.1 ").append(code);
        switch (code) {
            case 200:
                response.append(" OK");break;
            case 404:
                response.append(" NOT FOUND");break;
            case 500:
                response.append(" SERVER ERROR");break;
        }
        response.append(CRLF);
        response.append("Server:Alven MyServer0.0.2").append(CRLF);
        response.append("Date:"+ new Date()).append(CRLF);
        response.append("Content-type:").append(type).append(";charset=").append(charset).append(CRLF);
    }

    public void addContent(String content) {
        contentLength += content.getBytes().length;
        responseContent.append(content);
    }

    public void responsePush(int code, String charset) {
        creatResponseHead(code,type,charset);
        response.append("Content-Length:").append(contentLength).append(CRLF);
        response.append(CRLF).append(responseContent);
        try {
            writer.write(response.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setType(String type) {
        this.type = type;
    }

    public void close() throws IOException {
        writer.close();
    }
}

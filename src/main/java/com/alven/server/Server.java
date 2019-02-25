package com.alven.server;

import com.alven.server.web.Context;
import com.alven.server.entity.Dispatcher;
import com.alven.server.web.WebXMLParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Context context;
    private String serverStatus = "off";

    /**
     * 启动服务器
     * @param port 服务器端口
     */
    public void startUp(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverStatus = "success";
            init();
            recieve();
        } catch (IOException e) {
            e.printStackTrace();
            shutDown();
        }
    }

    private void init() {
        context = new Context();
        new WebXMLParser().initHandler(context);
    }


    private void recieve() {
        while(serverStatus.equals("success")) {
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(new Dispatcher(context,client,null,null)).start();
        }
    }

    public void shutDown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverStatus = "off";
    }

}

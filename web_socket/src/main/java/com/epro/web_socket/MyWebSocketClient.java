package com.epro.web_socket;

import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author zzy
 * @date 2020/1/15
 */
public class MyWebSocketClient extends org.java_websocket.client.WebSocketClient {
    public MyWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
        System.out.println("开始建立链接...");
    }


    @Override
    public void onMessage(String message) {
        System.out.println("检测到服务器请求...message： " + message);
    }


    @Override
    public void onError(Exception arg0) {
        arg0.printStackTrace();
        System.out.println("客户端发生错误,即将关闭!");
    }


    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        System.out.println("客户端已关闭!");
    }
}

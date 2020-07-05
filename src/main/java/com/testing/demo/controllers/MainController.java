package com.testing.demo.controllers;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class MainController extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Thread.sleep(3000); // simulated delay
        if (sessions.size() > 1) {
            //The second client has connected. Send the respective IP addresses.
            WebSocketSession theOtherClient = null;
            for (WebSocketSession webSocketSession : sessions) {
                if (!webSocketSession.getId().equals(session.getId())) {
                    //Send to the other initially logged in Client
                    TextMessage msg = new TextMessage("IP: " + session.getRemoteAddress());
                    webSocketSession.sendMessage(msg);
                    theOtherClient = webSocketSession;
                }
            }
            //Send to this connected client
            InetSocketAddress remoteIpOfOtherClient = null;
            if (theOtherClient != null) {
                remoteIpOfOtherClient = theOtherClient.getRemoteAddress();
            }
            TextMessage msg = new TextMessage("IP: " + remoteIpOfOtherClient);
            session.sendMessage(msg);
        }
        //session.sendMessage(new TextMessage(sessions.size() + ""));
        //session.getRemoteAddress();
        //session.sendMessage(msg);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
}

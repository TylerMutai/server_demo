package com.testing.demo.controllers;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class MainController extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Thread.sleep(3000); // simulated delay

        for (WebSocketSession webSocketSession : sessions) {
            if (!webSocketSession.getId().equals(session.getId())) {
              //Send to all other clients except the client sending the message
                webSocketSession.sendMessage(message);
            }
        }
        //session.getRemoteAddress();
        //session.sendMessage(msg);
        //session.sendMessage(new TextMessage(sessions.size() + ""));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }
}

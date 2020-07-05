package com.testing.demo.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

   /* @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOrigins("*");
        registry.addEndpoint("/chatting").setAllowedOrigins("*");
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }*/

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(greetingHandler(), "/server").setAllowedOrigins("*").withSockJS();
        registry.addHandler(greetingHandler(), "/server").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler greetingHandler() {
        return new MainController();
    }
}
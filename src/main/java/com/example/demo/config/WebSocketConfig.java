package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                // ✅ CORREÇÃO: Em vez de "*", especifique a origem exata da sua aplicação.
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefixo para destinos de mensagens vindas do cliente para o servidor (ex: /app/chat.sendMessage)
        registry.setApplicationDestinationPrefixes("/app");
        // Prefixo para os tópicos que o servidor enviará para os clientes (ex: /topic/public)
        registry.enableSimpleBroker("/topic");
    }
}
package com.example.demo.service;

import com.example.demo.model.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Cria automaticamente um construtor para campos 'final'
public class ChatService {

    private final ChatMessageRepository repository;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        // Aqui você poderia adicionar qualquer lógica de negócio.
        // Por exemplo: verificar por palavras proibidas, etc.
        return repository.save(chatMessage);
    }
}
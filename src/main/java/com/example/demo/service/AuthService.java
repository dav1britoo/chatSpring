package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String password) {
        // Verifica se o nome de usuário já existe
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe!");
        }

        User newUser = new User();
        newUser.setUsername(username);
        // Codifica a senha antes de salvar! Nunca salve senhas em texto plano.
        newUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(newUser);
    }
}
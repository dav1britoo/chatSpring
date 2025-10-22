package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Este Bean será executado na inicialização da aplicação
    @Bean
    CommandLineRunner createInitialUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o usuário já existe para não criar duplicatas
            if (userRepository.findByUsername("davi").isEmpty()) {
                User user = new User();
                user.setUsername("davi");
                // IMPORTANTE: Salve a senha SEMPRE codificada!
                user.setPassword(passwordEncoder.encode("12345"));
                userRepository.save(user);
                System.out.println(">>> Usuário 'davi' criado com senha '12345'");
            }
        };
    }
}
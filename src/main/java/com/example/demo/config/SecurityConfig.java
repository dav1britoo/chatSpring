package com.example.demo.config; // Certifique-se que o nome do pacote está correto

import com.example.demo.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para simplificar (não recomendado para produção sem tokens)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // Permite iframes para o SockJS funcionar
                .authorizeHttpRequests(auth -> auth
                        // ✅ MUDANÇA: Adiciona /api/auth/register à lista de permissões públicas.
                        // O padrão /api/auth/** permite qualquer endpoint futuro dentro de /auth (ex: /forgot-password).
                        .requestMatchers("/", "/index.html", "/ws/**", "/api/auth/**").permitAll()
                        // Qualquer outra requisição (ex: /api/users/me) precisa de autenticação.
                        .anyRequest().authenticated()
                )
                // Configura o formulário de login padrão do Spring Security
                .formLogin(form -> form
                        // Permite acesso à página de login gerada para todos
                        .permitAll()
                        // ✅ MUDANÇA: Redireciona para /index.html após um login bem-sucedido.
                        // O 'true' força o redirecionamento sempre para esta página.
                        .defaultSuccessUrl("/index.html", true)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define o BCrypt como o codificador de senhas. É o padrão da indústria.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // "Ensina" o Spring Security a usar nosso serviço (CustomUserDetailsService)
        // para encontrar usuários no banco de dados e a usar nosso codificador de senhas (PasswordEncoder)
        // para comparar as senhas durante o login.
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
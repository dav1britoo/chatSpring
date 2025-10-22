package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // ✅ NOVO: Endpoint para obter o usuário atualmente autenticado.
    // O Spring Security injeta automaticamente o usuário logado com @AuthenticationPrincipal.
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        if (currentUser == null) {
            // Isso acontece se o usuário não estiver logado
            return ResponseEntity.notFound().build();
        }
        // Retorna os dados do usuário (sem a senha, que é ignorada por padrão na serialização)
        return ResponseEntity.ok(currentUser);
    }
}
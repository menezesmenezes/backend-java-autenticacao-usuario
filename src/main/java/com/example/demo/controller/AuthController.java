package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite o front-end acessar sem bloqueio de CORS
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.cadastrar(usuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Erro ao cadastrar: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            String token = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());
            return ResponseEntity.ok(Map.of("token", token, "mensagem", "Login realizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("erro", e.getMessage()));
        }
    }
}
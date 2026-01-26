package com.vendas.vendas.controller;

import com.vendas.vendas.model.Usuario;
import com.vendas.vendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginData) {
        try {
            Usuario usuario = usuarioRepo.findByUsername(loginData.getUsername()).orElse(null);
            // ✅ SENHA PLANA (desenvolvimento)
            if (usuario != null && loginData.getPassword().equals(usuario.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                String token = "Bearer " + java.util.Base64.getEncoder().encodeToString(
                    (loginData.getUsername() + ":" + System.currentTimeMillis()).getBytes());
                response.put("token", token);
                response.put("perfil", usuario.getPerfil());
                return ResponseEntity.ok(response);
            }
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciais inválidas");
            return ResponseEntity.status(401).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro no login");
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuarioData) {
        try {
            if (usuarioRepo.findByUsername(usuarioData.getUsername()).isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Usuário já existe");
                return ResponseEntity.badRequest().body(error);
            }
            // ✅ SENHA PLANA (desenvolvimento)
            Usuario saved = usuarioRepo.save(usuarioData);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuário criado");
            response.put("id", saved.getIdusuario());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro ao registrar");
            return ResponseEntity.badRequest().body(error);
        }
    }
}

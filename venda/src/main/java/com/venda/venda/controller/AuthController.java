package com.venda.venda.controller;

import com.venda.venda.model.Usuario;
import com.venda.venda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;  // ✅ ADICIONADO
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /** ✅ LOGIN COM SESSION - ADMIN=TODOS | CLIENTE=SÓ SEUS */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String email = loginData.get("email");
        String senha = loginData.get("senha");
        
        System.out.println("🔍 LOGIN: " + email);
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // ✅ SENHA SIMPLES: 123456 para todos
            if ("123456".equals(senha)) {
                // ✅ ARMAZENA NA SESSION (ESSENCIAL para meuspedidos.html)
                session.setAttribute("perfil", usuario.getPerfil());
                session.setAttribute("emailUsuario", usuario.getEmail());
                
                // Security Context (mantém seu código original)
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    email, senha, 
                    List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil()))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                
                System.out.println("✅ LOGIN OK: " + email + " - Perfil: " + usuario.getPerfil());
                
                return ResponseEntity.ok(Map.of(
                    "success", true,      // ← login.html espera isso
                    "status", "ok",
                    "perfil", usuario.getPerfil(),
                    "email", usuario.getEmail(),
                    "nome", usuario.getNome(),
                    "message", "Login OK!"
                ));
            }
        }
        
        System.out.println("❌ LOGIN FALHOU: " + email);
        return ResponseEntity.status(401).body(Map.of(
            "success", false,
            "status", "error", 
            "message", "Credenciais inválidas"
        ));
    }
}

package com.venda.venda.controller;

import com.venda.venda.model.Cliente;
import com.venda.venda.model.Usuario;
import com.venda.venda.repository.ClienteRepository;
import com.venda.venda.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class HomeController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(HttpSession session) {
        String perfil = (String) session.getAttribute("perfil");
        String email = (String) session.getAttribute("emailUsuario");
        
        if (perfil == null || email == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Faça login novamente"));
        }
        
        System.out.println("✅ SESSION OK: " + email + " - " + perfil);
        return ResponseEntity.ok(Map.of(
            "perfil", perfil.toUpperCase(),
            "email", email,
            "autenticado", true
        ));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Map<String, String> dados) {
        try {
            System.out.println("📥 DADOS RECEBIDOS: " + dados); // ✅ DEBUG
            
            // ✅ EXTRAÇÃO SEGURA com validação
            String email = dados != null ? dados.get("email") : null;
            String nome = dados != null ? dados.get("nome") : null;
            String senha = dados != null ? dados.get("senha") : null;
            String cpf = dados != null ? dados.get("cpf") : null;
            String perfil = dados != null ? dados.get("perfil") : null;

            // ✅ VALIDAÇÕES ROBUSTAS
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email é obrigatório"));
            }
            if (nome == null || nome.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Nome é obrigatório"));
            }
            if (senha == null || senha.trim().length() < 6) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Senha deve ter pelo menos 6 caracteres"));
            }
            if (perfil == null || (!"CLIENTE".equalsIgnoreCase(perfil) && !"ADMIN".equalsIgnoreCase(perfil))) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Perfil deve ser CLIENTE ou ADMIN"));
            }

            // ✅ VERIFICA EMAIL DUPLICADO
            if (usuarioRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email já cadastrado"));
            }

            // ✅ CRIPTOGRAFIA SENHA
            String senhaCriptografada = passwordEncoder.encode(senha);
            
            // ✅ USUÁRIO
            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail(email.trim());
            novoUsuario.setNome(nome.trim());
            novoUsuario.setSenha(senhaCriptografada);
            novoUsuario.setPerfil(perfil.toUpperCase());

            // ✅ LÓGICA CLIENTE vs ADMIN
            if ("CLIENTE".equals(novoUsuario.getPerfil())) {
                if (cpf == null || cpf.trim().isEmpty()) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("message", "CPF é obrigatório para CLIENTE"));
                }
                
                Cliente cliente = new Cliente();
                cliente.setEmail(email.trim());
                cliente.setNome(nome.trim());
                cliente.setCpf(cpf.trim().replaceAll("[^0-9]", "")); // ✅ Limpa CPF
                clienteRepository.save(cliente);
                System.out.println("📋 CLIENTE + CPF salvo: " + email);
            } else {
                System.out.println("👑 ADMIN salvo: " + email);
            }

            usuarioRepository.save(novoUsuario);
            
            System.out.println("✅ CADASTRO CONCLUÍDO: " + email + " - " + perfil);
            
            return ResponseEntity.ok(Map.of(
                "message", "Cadastro realizado com sucesso!",
                "email", email,
                "perfil", perfil.toUpperCase(),
                "cliente_criado", "CLIENTE".equals(novoUsuario.getPerfil())
            ));
            
        } catch (Exception e) {
            System.err.println("💥 ERRO CADASTRO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Map.of("message", "Erro interno: " + e.getMessage()));
        }
    }
}


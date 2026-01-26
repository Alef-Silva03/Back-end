package com.vendasfinal.sistema.service;

import com.vendasfinal.sistema.model.*;
import com.vendasfinal.sistema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired 
    private UsuarioRepository usuarioRepository;
    
    @Autowired 
    private ClienteRepository clienteRepository;
    
    @Autowired 
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrar(Cliente cliente, String role) {
        // 1. Verificar se o e-mail já está cadastrado
        if (usuarioRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está cadastrado no sistema.");
        }

        // 2. Limpar máscaras (CPF e CEP) para salvar apenas números
        cliente.setCpf(apenasNumeros(cliente.getCpf()));
        cliente.setCep(apenasNumeros(cliente.getCep()));

        // 3. Criptografar a senha
        String senhaCripto = passwordEncoder.encode(cliente.getSenha());

        if ("CLIENTE".equals(role)) {
            cliente.setSenha(senhaCripto);
            cliente.setRole("CLIENTE");
            // Salva na tabela Cliente (que herda de Usuario)
            return clienteRepository.save(cliente);
        } else {
            // Caso seja ADMIN, criamos um objeto Usuario limpo
            Usuario admin = new Usuario();
            admin.setEmail(cliente.getEmail());
            admin.setSenha(senhaCripto);
            admin.setRole("ADMIN");
            return usuarioRepository.save(admin);
        }
    }

    // Método auxiliar para garantir integridade dos dados (sem pontos ou traços)
    private String apenasNumeros(String valor) {
        if (valor == null) return null;
        return valor.replaceAll("\\D", ""); 
    }

    @Transactional
    public String gerarTokenRecuperacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));
        
        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuarioRepository.save(usuario);
        return token;
    }
}
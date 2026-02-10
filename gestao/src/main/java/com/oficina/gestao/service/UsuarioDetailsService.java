package com.oficina.gestao.service;

import com.oficina.gestao.model.Usuario;
import com.oficina.gestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Busca no banco de dados
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        // 2. Constrói o UserDetails usando o Builder (mais limpo e evita erros de Authority)
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha()) // Deve ser o hash $2a$...
                .authorities(usuario.getTipo().name()) // Define ADMINISTRADOR, FUNCIONARIO, etc.
                .build();
    }
}
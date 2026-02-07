package com.oficina.gestao.service;


import com.oficina.gestao.model.Usuario;
import com.oficina.gestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(email);

        // Se não encontrar, lança exceção que o Spring Security captura
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email);
        }

        // Retorna um objeto User do Spring Security com os dados do seu banco
        return new User(
            usuario.getEmail(), 
            usuario.getSenha(), // Esta senha DEVE estar criptografada com BCrypt no banco
            new ArrayList<>()   // Lista de permissões (vazia por enquanto)
        );
    }
}
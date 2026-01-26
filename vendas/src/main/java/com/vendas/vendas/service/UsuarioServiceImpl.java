package com.vendas.vendas.service;

import com.vendas.vendas.model.Usuario;
import com.vendas.vendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired 
    private UsuarioRepository repository;
    
    @Override 
    public Usuario findByUsername(String username) {
        return repository.findByUsername(username)
            .orElse(null);  // ✅ SIMPLES null (sem exception)
    }
    
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }
}

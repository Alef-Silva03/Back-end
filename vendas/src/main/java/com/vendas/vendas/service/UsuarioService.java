package com.vendas.vendas.service;

import com.vendas.vendas.model.Usuario;

public interface UsuarioService {
    Usuario findByUsername(String username);
    Usuario save(Usuario usuario);
}

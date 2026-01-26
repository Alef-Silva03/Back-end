package com.venda3.venda3.repository;

import com.venda3.venda3.model.Inquilino;
import com.venda3.venda3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
    
    // Busca o perfil de cliente vinculado a um objeto usuário específico
    Inquilino findByUsuario(Usuario usuario);
    
    // Busca o cliente pelo ID do usuário (útil para rotas de API)
    Inquilino findByUsuarioId(Long usuarioId);
}
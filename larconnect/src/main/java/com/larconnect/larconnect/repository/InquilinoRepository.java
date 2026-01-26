package com.larconnect.larconnect.repository;

import com.larconnect.larconnect.model.Inquilino;
import com.larconnect.larconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
    
    // Busca o perfil de cliente vinculado a um objeto usuário específico
    Inquilino findByUsuario(Usuario usuario);
    
    // Busca o cliente pelo ID do usuário (útil para rotas de API)
    Inquilino findByUsuarioId(Long usuarioId);
}
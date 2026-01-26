package com.larconnect.larconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.larconnect.larconnect.model.Sindico;
import com.larconnect.larconnect.model.Usuario;

@Repository
public interface SindicoRepository extends JpaRepository<Sindico, Long> {
    
    // Busca o perfil de cliente vinculado a um objeto usuário específico
    Sindico findByUsuario(Usuario usuario);
    
    // Busca o cliente pelo ID do usuário (útil para rotas de API)
    Sindico findByUsuarioId(Long usuarioId);
}

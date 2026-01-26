package com.larconnect.larconnect.repository;

import com.larconnect.larconnect.model.Proprietario;
import com.larconnect.larconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    
    // Busca o perfil de cliente vinculado a um objeto usuário específico
    Proprietario findByUsuario(Usuario usuario);
    
    // Busca o cliente pelo ID do usuário (útil para rotas de API)
    Proprietario findByUsuarioId(Long usuarioId);
}
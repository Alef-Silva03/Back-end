package com.larconnect.larconnect.repository;

import com.larconnect.larconnect.model.Funcionario;
import com.larconnect.larconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
    // Busca o perfil de cliente vinculado a um objeto usuário específico
    Funcionario findByUsuario(Usuario usuario);
    
    // Busca o cliente pelo ID do usuário (útil para rotas de API)
    Funcionario findByUsuarioId(Long usuarioId);
}
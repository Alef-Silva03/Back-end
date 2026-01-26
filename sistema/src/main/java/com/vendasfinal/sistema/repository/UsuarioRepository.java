package com.vendasfinal.sistema.repository;

import com.vendasfinal.sistema.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Hibernate gera: SELECT * FROM usuarios WHERE email = ?
    Optional<Usuario> findByEmail(String email);
    
    // Hibernate gera: SELECT * FROM usuarios WHERE reset_token = ?
    Optional<Usuario> findByResetToken(String token);
}
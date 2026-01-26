package com.sistema.login.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.login.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // ✅ ESSE MÉTODO DEVE EXISTIR
    Optional<Usuario> findByEmail(String email);
}

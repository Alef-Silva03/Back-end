package com.projeto.larconnect.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projeto.larconnect.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmailIgnoreCase(String email);

	Optional<Usuario> findByResetToken(String resetToken);

	boolean existsByEmailIgnoreCase(String email);
}

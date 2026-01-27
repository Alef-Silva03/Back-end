package com.larconnect.larconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.larconnect.larconnect.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// Spring cria automaticamente: SELECT * FROM usuario WHERE email = ?
	Usuario findByEmail(String email);

	// ESTE MÉTODO É ESSENCIAL PARA O RESET FUNCIONAR:
	Usuario findByResetToken(String resetToken);

	// Usado no cadastro para evitar e-mails duplicados
	boolean existsByEmail(String email);
	boolean existsByCpf(String cpf);
}
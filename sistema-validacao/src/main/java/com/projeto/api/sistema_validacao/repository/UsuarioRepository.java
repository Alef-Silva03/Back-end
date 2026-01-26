
package com.projeto.api.sistema_validacao.repository;

import com.projeto.api.sistema_validacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
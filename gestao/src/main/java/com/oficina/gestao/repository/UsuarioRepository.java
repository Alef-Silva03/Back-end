package com.oficina.gestao.repository;


import com.oficina.gestao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // O Spring transformará isso em: SELECT * FROM usuario WHERE email = ?
    Usuario findByEmail(String email);
}
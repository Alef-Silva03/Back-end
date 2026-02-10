package com.oficina.gestao.repository;


import com.oficina.gestao.model.Usuario;
import com.oficina.gestao.model.TipoUsuario; // Importe o seu Enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);

    // CORREÇÃO AQUI: Troque Object por TipoUsuario
    List<Usuario> findByTipo(TipoUsuario tipo);
}
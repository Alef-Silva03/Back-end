package com.vendas.vendas.repository;

import com.vendas.vendas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);
    
    // ✅ SIMPLES - SEM JOIN (funciona com FK idcliente)
    @Query("SELECT u FROM Usuario u WHERE u.perfil = 'CLIENTE'")
    List<Usuario> findClientesComDados();
    
    List<Usuario> findByPerfil(String perfil);
}

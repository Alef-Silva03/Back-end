package com.venda3.venda3.repository;

import com.venda3.venda3.model.Proprietario;
import com.venda3.venda3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    
    // Busca o perfil de cliente vinculado a um objeto usuário específico
    Proprietario findByUsuario(Usuario usuario);
    
    // Busca o cliente pelo ID do usuário (útil para rotas de API)
    Proprietario findByUsuarioId(Long usuarioId);
}
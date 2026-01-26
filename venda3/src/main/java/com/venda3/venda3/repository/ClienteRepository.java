package com.venda3.venda3.repository;



import com.venda3.venda3.model.Cliente;
import com.venda3.venda3.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Busca o perfil de cliente vinculado a um objeto usuário específico
    Cliente findByUsuario(Usuario usuario);
    
    // Busca o cliente pelo ID do usuário (útil para rotas de API)
    Cliente findByUsuarioId(Long usuarioId);
}
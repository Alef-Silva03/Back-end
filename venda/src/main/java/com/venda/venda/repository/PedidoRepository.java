package com.venda.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.venda.venda.model.Pedido;
import com.venda.venda.model.Cliente;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // ✅ QUERY PERSONALIZADA - FUNCIONA 100%
    @Query("SELECT p FROM Pedido p WHERE p.cliente.email = :email")
    List<Pedido> findByClienteEmail(@Param("email") String email);
    
    // Método automático JPA (também funciona)
    List<Pedido> findByCliente(Cliente cliente);
}

package com.vendasfinal.sistema.repository;

import com.vendasfinal.sistema.model.Pedido;
import com.vendasfinal.sistema.model.StatusPedido;
import com.vendasfinal.sistema.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Hibernate gera a busca filtrada por cliente
    List<Pedido> findByCliente(Cliente cliente);

    // Hibernate gera o count automaticamente filtrando pelo Enum Status
    long countByStatus(StatusPedido status);

    // Para buscar todos os pedidos de um status específico (ex: CONFIRMADO)
    List<Pedido> findByStatus(StatusPedido status);
    
    // Hibernate gera: SELECT * FROM pedidos ORDER BY data_pedido DESC
    List<Pedido> findAllByOrderByDataPedidoDesc();
}
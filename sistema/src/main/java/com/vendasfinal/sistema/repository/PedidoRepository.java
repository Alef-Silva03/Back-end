package com.vendasfinal.sistema.repository;

import com.vendasfinal.sistema.model.Pedido;
import com.vendasfinal.sistema.model.Cliente; // IMPORTANTE: Adicione esta importação
import com.vendasfinal.sistema.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // 1. Resolve o erro do LojaController (Meus Pedidos)
    List<Pedido> findByCliente(Cliente cliente);

    // 2. Resolve o erro do AdminController (Tabela de recentes)
    List<Pedido> findTop5ByOrderByIdDesc();

    // 3. Métodos usados pelo DashBoardService (Métricas faturamento/pendentes)
    List<Pedido> findByStatus(StatusPedido status);
    
    long countByStatus(StatusPedido status);
}
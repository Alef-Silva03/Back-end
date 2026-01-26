package com.vendas.vendas.repository;

import com.vendas.vendas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // ✅ CORRETO: Usa nome do campo direto
    List<Pedido> findByIdusuario(Long idusuario);
    
    // ✅ OU por relacionamento (se existir @ManyToOne)
    // List<Pedido> findByUsuarioIdusuario(Long idusuario);
}

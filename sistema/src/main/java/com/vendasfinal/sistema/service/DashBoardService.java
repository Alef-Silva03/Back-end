package com.vendasfinal.sistema.service;

import com.vendasfinal.sistema.model.Pedido;
import com.vendasfinal.sistema.model.StatusPedido;
import com.vendasfinal.sistema.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {

    @Autowired 
    private PedidoRepository pedidoRepository;

    /**
     * Calcula o total de todas as vendas com status CONFIRMADO.
     * Alterado de BigDecimal para Double para manter compatibilidade com o Model Pedido.
     */
    public Double calcularTotalVendasConfirmadas() {
        // Busca a lista de pedidos com status CONFIRMADO
        List<Pedido> pedidosConfirmados = pedidoRepository.findByStatus(StatusPedido.CONFIRMADO);
        
        // Usamos mapToDouble para converter o Double objeto em primitivo e somar
        return pedidosConfirmados.stream()
                .mapToDouble(p -> p.getValorTotal() != null ? p.getValorTotal() : 0.0)
                .sum();
    }

    public long contarPedidosPorStatus(StatusPedido status) {
        return pedidoRepository.countByStatus(status);
    }
}

package com.vendasfinal.sistema.service;

import com.vendasfinal.sistema.model.Pedido;
import com.vendasfinal.sistema.model.StatusPedido;
import com.vendasfinal.sistema.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashBoardService {

    @Autowired 
    private PedidoRepository pedidoRepository;

    /**
     * Calcula o total de todas as vendas com status CONFIRMADO.
     */
    @Transactional(readOnly = true)
    public Double calcularTotalVendasConfirmadas() {
        List<Pedido> pedidosConfirmados = pedidoRepository.findByStatus(StatusPedido.CONFIRMADO);
        
        if (pedidosConfirmados == null || pedidosConfirmados.isEmpty()) {
            return 0.0;
        }

        return pedidosConfirmados.stream()
                .mapToDouble(p -> p.getValorTotal() != null ? p.getValorTotal() : 0.0)
                .sum();
    }

    /**
     * Conta quantos pedidos existem com um determinado status.
     */
    @Transactional(readOnly = true)
    public long contarPedidosPorStatus(StatusPedido status) {
        return pedidoRepository.countByStatus(status);
    }
}
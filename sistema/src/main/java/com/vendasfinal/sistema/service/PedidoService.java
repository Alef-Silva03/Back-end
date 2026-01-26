package com.vendasfinal.sistema.service;

import com.vendasfinal.sistema.model.*;
import com.vendasfinal.sistema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PedidoService {

    @Autowired 
    private PedidoRepository pedidoRepository;
    
    @Autowired 
    private EstoqueService estoqueService;

    @Transactional
    public Pedido realizarPedido(Pedido pedido) {
        pedido.setStatus(StatusPedido.PENDENTE);
        
        // Garantimos que a lista de itens não é nula antes de iterar
        if (pedido.getItens() != null) {
            pedido.getItens().forEach(item -> {
                // Verificamos se o produto e o ID existem para evitar NullPointerException
                if (item.getProduto() != null && item.getProduto().getId() != null) {
                    estoqueService.baixarEstoque(item.getProduto().getId(), item.getQuantidade());
                }
            });
        }
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Se o pedido está sendo CANCELADO agora, mas antes não estava:
        if (novoStatus == StatusPedido.CANCELADO && !StatusPedido.CANCELADO.equals(pedido.getStatus())) {
            if (pedido.getItens() != null) {
                pedido.getItens().forEach(item -> {
                    if (item.getProduto() != null) {
                        estoqueService.adicionarEstoque(item.getProduto().getId(), item.getQuantidade());
                    }
                });
            }
        }
        
        pedido.setStatus(novoStatus);
        pedidoRepository.save(pedido);
    }
}
package com.vendas.vendas.service;

import com.vendas.vendas.model.Pedido;
import java.util.List;

public interface PedidoService {
    List<Pedido> findAll();
    Pedido save(Pedido pedido);
    Pedido findById(Long id);
    void deleteById(Long id);
    List<Pedido> findByCliente(Long idusuario);  // idusuario do cliente
}

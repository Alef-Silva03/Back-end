package com.venda.venda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.venda.venda.repository.PedidoRepository;
import com.venda.venda.model.Pedido;
import com.venda.venda.model.Cliente;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repository;

    public List<Pedido> listAll() {
        return repository.findAll();
    }
    public List<Pedido> findByClienteEmail(String email) {
        return repository.findByClienteEmail(email);
    }
    // ✅ NOVO MÉTODO PARA CLIENTE
    public List<Pedido> findByCliente(Cliente cliente) {
        return repository.findByCliente(cliente);
    }

    public Optional<Pedido> findById(Long id) {
        return repository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

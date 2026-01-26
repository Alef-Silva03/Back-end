package com.vendas.vendas.service;

import com.vendas.vendas.model.Pedido;
import com.vendas.vendas.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;  // ✅ Date (não LocalDate)
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Override
    public List<Pedido> findAll() {
        return pedidoRepo.findAll();
    }

    @Override
    public Pedido save(Pedido pedido) {
        if (pedido.getData() == null) {
            pedido.setData(new Date());  // ✅ Date.now()
        }
        return pedidoRepo.save(pedido);
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        if (pedidoRepo.existsById(id)) {
            pedidoRepo.deleteById(id);
        }
    }

    // ✅ CORRIGIDO: findByIdusuario (do Repository)
    @Override
    public List<Pedido> findByCliente(Long idusuario) {
        return pedidoRepo.findByIdusuario(idusuario);  // ✅ findByIdusuario
    }
}

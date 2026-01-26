package com.vendas.vendas.controller;

import com.vendas.vendas.model.Pedido;
import com.vendas.vendas.repository.PedidoRepository;
import com.vendas.vendas.repository.ProdutoRepository;
import com.vendas.vendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("*")
public class PedidoController {
    
    @Autowired
    private PedidoRepository pedidoRepo;
    
    // ✅ FUNCIONA AGORA!
    @GetMapping("/usuario/{id}")
    public List<Pedido> listarPorUsuario(@PathVariable Long id) {
        return pedidoRepo.findByIdusuario(id);
    }
}


    
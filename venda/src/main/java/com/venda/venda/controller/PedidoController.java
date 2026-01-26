package com.venda.venda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import com.venda.venda.service.PedidoService;
import com.venda.venda.model.Pedido;
import com.venda.venda.model.Cliente;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    
    @Autowired
    private PedidoService service;

    /** ✅ /api/pedidos/meus - ADMIN=TODOS | CLIENTE=SÓ SEUS */
    @GetMapping("/meus")
    public ResponseEntity<List<Pedido>> listarMeusPedidos(HttpSession session) {
        String perfil = (String) session.getAttribute("perfil");
        String email = (String) session.getAttribute("emailUsuario");
        
        // 🔍 DEBUG no console
        System.out.println("🔍 DEBUG - Perfil: '" + perfil + "'");
        System.out.println("🔍 DEBUG - Email: '" + email + "'");
        
        // ADMIN vê TUDO
        if ("ADMIN".equalsIgnoreCase(perfil) || "ADM".equalsIgnoreCase(perfil)) {
            System.out.println("✅ ADMIN - Retornando TODOS os pedidos");
            return ResponseEntity.ok(service.listAll());
        }
        
        // CLIENTE SEM EMAIL → 401
        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ CLIENTE sem email na session - 401");
            return ResponseEntity.status(401).build();
        }
        
        System.out.println("✅ CLIENTE " + email + " - Buscando pedidos...");
        List<Pedido> pedidosCliente = service.findByClienteEmail(email);
        System.out.println("✅ Encontrados " + pedidosCliente.size() + " pedidos para " + email);
        
        return ResponseEntity.ok(pedidosCliente);
    }
    
    @GetMapping
    public ResponseEntity<List<Pedido>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody Pedido pedido) {
        Pedido saved = service.save(pedido);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido pedido) {
        if (service.findById(id).isPresent()) {
            pedido.setId(id);
            return ResponseEntity.ok(service.save(pedido));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

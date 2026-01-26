package com.vendas.vendas.controller;

import com.vendas.vendas.model.Cliente;
import com.vendas.vendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")  // ✅ SEM allowCredentials
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteRepo.findAll();
        System.out.println("📋 Clientes carregados: " + clientes.size());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepo.findById(id);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente saved = clienteRepo.save(cliente);
            System.out.println("✅ Cliente criado: " + saved.getNome());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            System.out.println("❌ Erro criar cliente: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> clienteOpt = clienteRepo.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (clienteDetails.getNome() != null) cliente.setNome(clienteDetails.getNome().trim());
            if (clienteDetails.getCpf() != null) cliente.setCpf(clienteDetails.getCpf());
            if (clienteDetails.getEmail() != null) cliente.setEmail(clienteDetails.getEmail());
            if (clienteDetails.getTelefone() != null) cliente.setTelefone(clienteDetails.getTelefone());
            
            Cliente updated = clienteRepo.save(cliente);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (clienteRepo.existsById(id)) {
            clienteRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


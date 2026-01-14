package com.exerc.exerc.controller;

import com.exerc.exerc.model.Cliente;
import com.exerc.exerc.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	// Listar Todos
	@GetMapping
	public ResponseEntity<List<Cliente>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	// Buscar por ID
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
	// CRIAR NOVO
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.save(cliente));
    }
    
 // ATUALIZAR  CORRIGIDO
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        return service.findById(id)
                .map(clienteExistente -> {
                    // Copia os dados do JSON para o cliente existente
                    clienteExistente.setNomecliente(clienteAtualizado.getNomecliente());
                    clienteExistente.setCpf(clienteAtualizado.getCpf());
                    clienteExistente.setEmail(clienteAtualizado.getEmail());
                    // Adicione outros campos conforme sua entidade
                    
                    return ResponseEntity.ok(service.save(clienteExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
 // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
	
}

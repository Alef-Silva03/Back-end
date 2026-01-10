package com.vendas.vendas.controller;

import com.vendas.vendas.model.Produto;
import com.vendas.vendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    
    @Autowired
    private ProdutoService service;

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CRIAR NOVO
    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody Produto produto) {
        return ResponseEntity.ok(service.save(produto));
    }

    // ATUALIZAR  CORRIGIDO
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return service.findById(id)
                .map(produtoExistente -> {
                    // Copia os dados do JSON para o produto existente
                    produtoExistente.setNomeproduto(produtoAtualizado.getNomeproduto());
                    produtoExistente.setPreco(produtoAtualizado.getPreco());
                    // Adicione outros campos conforme sua entidade
                    
                    return ResponseEntity.ok(service.save(produtoExistente));
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
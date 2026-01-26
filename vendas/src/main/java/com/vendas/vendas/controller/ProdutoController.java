package com.vendas.vendas.controller;

import com.vendas.vendas.model.Produto;
import com.vendas.vendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")  // ✅ SEM allowCredentials
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepo;

    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoRepo.findAll();
        System.out.println("📋 Produtos carregados: " + produtos.size());
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepo.findById(id);
        return produto.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        try {
            if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Produto saved = produtoRepo.save(produto);
            System.out.println("✅ Produto criado: " + saved.getNome());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            System.out.println("❌ Erro criar produto: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        Optional<Produto> produtoOpt = produtoRepo.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            if (produtoDetails.getNome() != null) produto.setNome(produtoDetails.getNome().trim());
            if (produtoDetails.getPreco() != null) produto.setPreco(produtoDetails.getPreco());
            
            Produto updated = produtoRepo.save(produto);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (produtoRepo.existsById(id)) {
            produtoRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

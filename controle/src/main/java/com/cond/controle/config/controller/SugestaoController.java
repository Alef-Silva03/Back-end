package com.cond.controle.config.controller;

import com.cond.controle.model.Sugestao;
import com.cond.controle.repository.SugestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sugestoes")
@CrossOrigin(origins = "*")
public class SugestaoController {

    @Autowired
    private SugestaoRepository repository;

    @GetMapping
    public List<Sugestao> listar() {
        return repository.findAllByOrderByDataDesc();
    }

    @PostMapping
    public ResponseEntity<Sugestao> criar(@RequestBody Sugestao sugestao) {
        return ResponseEntity.ok(repository.save(sugestao));
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<?> resolver(@PathVariable Long id) {
        return repository.findById(id).map(s -> {
            s.setStatus("RESOLVIDO");
            repository.save(s);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
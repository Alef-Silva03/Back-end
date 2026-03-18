package com.cond.controle.config.controller;


import com.cond.controle.model.Encomenda;
import com.cond.controle.repository.EncomendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/encomendas")
@CrossOrigin(origins = "*")
public class EncomendaController {

    @Autowired
    private EncomendaRepository repository;

    @GetMapping
    public List<Encomenda> listarTodas() {
        return repository.findAll();
    }

    @PostMapping
    public Encomenda registrar(@RequestBody Encomenda encomenda) {
        return repository.save(encomenda);
    }

    @PutMapping("/{id}/retirar")
    public void marcarComoRetirada(@PathVariable Long id) {
        repository.findById(id).ifPresent(e -> {
            e.setRetirada(true);
            repository.save(e);
        });
    }
}
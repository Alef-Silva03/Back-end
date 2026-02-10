package com.oficina.gestao.controller;


import com.oficina.gestao.model.Veiculo;
import com.oficina.gestao.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    @GetMapping
    public List<Veiculo> listarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Veiculo veiculo) {
        try {
            // Verifica se a placa já existe
            if (repository.findByPlaca(veiculo.getPlaca()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Veículo com esta placa já cadastrado.");
            }
            
            Veiculo salvo = repository.save(veiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar: " + e.getMessage());
        }
    }

    @GetMapping("/cliente/{id}")
    public List<Veiculo> listarPorCliente(@PathVariable Long id) {
        return repository.findByClienteId(id);
    }
}
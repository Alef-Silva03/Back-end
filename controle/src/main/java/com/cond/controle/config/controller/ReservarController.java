package com.cond.controle.config.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cond.controle.model.Reserva;
import com.cond.controle.repository.ReservaRepository;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservarController {

    @Autowired
    private ReservaRepository repository;

    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody Reserva reserva) {
        // Regra de Negócio: Impedir data retroativa ou duplicada
        if (repository.existsByEspacoAndData(reserva.getEspaco(), reserva.getData())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Este espaço já está reservado para esta data.");
        }
        
        Reserva novaReserva = repository.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> listarPorUsuario(@PathVariable Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
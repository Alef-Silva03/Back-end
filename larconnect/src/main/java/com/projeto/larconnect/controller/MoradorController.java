package com.projeto.larconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.larconnect.model.Morador;
import com.projeto.larconnect.service.MoradorService;
import java.util.List;

@RestController
@RequestMapping("/api/morador")
@CrossOrigin(origins = "*")
public class MoradorController {

	private final MoradorService service;

	public MoradorController(MoradorService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<Morador>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Morador> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Morador> save(@RequestBody Morador morador) {
		Morador saved = service.save(morador);
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Morador> update(@PathVariable Long id, @RequestBody Morador morador) {
		morador.setId(id);
		return ResponseEntity.ok(service.save(morador));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}

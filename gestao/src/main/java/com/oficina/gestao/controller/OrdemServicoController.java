package com.oficina.gestao.controller;

import com.oficina.gestao.model.StatusOS;
import com.oficina.gestao.model.OrdemServico;
import com.oficina.gestao.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens-servico")
@CrossOrigin(origins = "*")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService osService;

    @GetMapping
    public List<OrdemServico> listar() {
        return osService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> buscar(@PathVariable Long id) {
        OrdemServico os = osService.buscarPorId(id);
        return os != null ? ResponseEntity.ok(os) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public OrdemServico criar(@RequestBody OrdemServico os) {
        // CORREÇÃO: Atribua o valor usando o Enum, não uma String.
        if (os.getStatus() == null) {
            os.setStatus(StatusOS.ORCAMENTO);
        }
        return osService.salvarOS(os);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        try {
            OrdemServico osFinalizada = osService.finalizarOS(id);
            return ResponseEntity.ok(osFinalizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServico> atualizar(@PathVariable Long id, @RequestBody OrdemServico os) {
        // CORREÇÃO: Boa prática verificar se o recurso existe antes de atualizar
        OrdemServico existente = osService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        os.setId(id);
        return ResponseEntity.ok(osService.salvarOS(os));
    }
}
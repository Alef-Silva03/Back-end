package com.oficina.gestao.controller;

import com.oficina.gestao.model.OrdemServico;
import com.oficina.gestao.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens-servico")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService osService;

    /**
     * Retorna apenas as ordens do cliente logado.
     * O Spring Security injeta o objeto 'auth' automaticamente se o usuário estiver logado.
     */
    @GetMapping("/meus-servicos")
    public ResponseEntity<List<OrdemServico>> listarMeusServicos(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String emailLogado = auth.getName();
        List<OrdemServico> minhasOrdens = osService.listarPorEmailCliente(emailLogado);
        return ResponseEntity.ok(minhasOrdens);
    }

    // Listar todas (Acesso geralmente restrito a ADMIN/FUNCIONARIO via SecurityConfig)
    @GetMapping
    public ResponseEntity<List<OrdemServico>> listar() {
        return ResponseEntity.ok(osService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> buscar(@PathVariable Long id) {
        OrdemServico os = osService.buscarPorId(id);
        return os != null ? ResponseEntity.ok(os) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrdemServico> criar(@RequestBody OrdemServico os) {
        try {
            if (os.getStatus() == null || os.getStatus().isEmpty()) {
                os.setStatus("ORÇAMENTO");
            }
            // O Service deve chamar os.calcularTotal() internamente
            OrdemServico novaOs = osService.salvarOS(os);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        try {
            OrdemServico osFinalizada = osService.finalizarOS(id);
            return ResponseEntity.ok(osFinalizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServico> atualizar(@PathVariable Long id, @RequestBody OrdemServico os) {
        OrdemServico existente = osService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        
        os.setId(id);
        // Garantimos que o status ou dados sensíveis sejam validados no Service
        return ResponseEntity.ok(osService.salvarOS(os));
    }
}
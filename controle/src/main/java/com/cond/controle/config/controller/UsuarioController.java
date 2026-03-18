package com.cond.controle.config.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cond.controle.model.Anuncio;
import com.cond.controle.model.Usuario;
import com.cond.controle.repository.AnuncioRepository;
import com.cond.controle.repository.UsuarioRepository;
import com.cond.controle.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    // ==========================================
    // 1. AUTENTICAÇÃO E CADASTRO
    // ==========================================

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        Usuario user = service.autenticar(loginData.get("email"), loginData.get("senha"));
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha incorretos.");
    }

    // ==========================================
    // 2. GESTÃO DE MORADORES (SÍNDICO)
    // ==========================================

    @GetMapping
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id) {
        return repository.findById(id)
                .map(u -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * NOVO: Atualiza o status financeiro do morador
     * Chamado pelo botão "Salvar Alterações" no usuarios.html
     */
    @PutMapping("/{id}/status-financeiro")
    public ResponseEntity<?> atualizarStatusFinanceiro(@PathVariable Long id, @RequestParam boolean inadimplente) {
        return repository.findById(id)
                .map(u -> {
                    u.setInadimplente(inadimplente);
                    repository.save(u);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // ==========================================
    // 3. MÓDULO DE CLASSIFICADOS (ANÚNCIOS)
    // ==========================================

    @PostMapping("/anuncios")
    public ResponseEntity<?> criarAnuncio(@RequestBody Anuncio anuncio) {
        try {
            if (anuncio.getAnunciante() == null || anuncio.getAnunciante().getId() == null) {
                return ResponseEntity.badRequest().body("ID do anunciante não fornecido.");
            }

            // Busca o usuário real para associar ao anúncio
            Usuario usuarioDoBanco = repository.findById(anuncio.getAnunciante().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            anuncio.setAnunciante(usuarioDoBanco);
            anuncio.setAprovado(false); 

            Anuncio salvo = anuncioRepository.save(anuncio);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erro no servidor: " + e.getMessage());
        }
    }

    @GetMapping("/anuncios/pendentes")
    public List<Anuncio> listarAnunciosPendentes() {
        return anuncioRepository.findByAprovadoFalse();
    }

    @GetMapping("/anuncios/aprovados")
    public List<Anuncio> listarAnunciosAprovados() {
        return anuncioRepository.findByAprovadoTrue();
    }

    @PutMapping("/anuncios/{id}/aprovar")
    public ResponseEntity<?> aprovarAnuncio(@PathVariable Long id) {
        return anuncioRepository.findById(id)
                .map(anuncio -> {
                    anuncio.setAprovado(true);
                    anuncioRepository.save(anuncio);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/anuncios/{id}/recusar")
    public ResponseEntity<?> recusarAnuncio(@PathVariable Long id) {
        if (anuncioRepository.existsById(id)) {
            anuncioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
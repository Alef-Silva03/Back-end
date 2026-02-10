package com.oficina.gestao.controller;

import com.oficina.gestao.model.Usuario;
import com.oficina.gestao.model.TipoUsuario; // Certifique-se de importar o Enum
import com.oficina.gestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        try {
            if (repository.findByEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este e-mail já está cadastrado.");
            }

            if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
                return ResponseEntity.badRequest().body("A senha é obrigatória.");
            }

            usuario.setSenha(encoder.encode(usuario.getSenha()));
            Usuario usuarioSalvo = repository.save(usuario);
            
            usuarioSalvo.setSenha(null); 
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return repository.findByEmail(auth.getName())
                .map(usuario -> {
                    usuario.setSenha(null);
                    return ResponseEntity.ok(usuario);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = repository.findAll().stream()
                .peek(u -> u.setSenha(null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    /**
     * CORREÇÃO: O parâmetro foi alterado de String para TipoUsuario.
     * O Spring fará o "bind" automático da String da URL para o Enum.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Usuario>> listarPorTipo(@PathVariable TipoUsuario tipo) {
        try {
            // Agora passamos o Enum diretamente para o Repository
            List<Usuario> filtrados = repository.findByTipo(tipo);
            
            filtrados.forEach(u -> u.setSenha(null));
            
            return ResponseEntity.ok(filtrados);
        } catch (Exception e) {
            // Se o usuário digitar um tipo que não existe no Enum na URL, retorna 400
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
package com.oficina.gestao.controller;

import com.oficina.gestao.model.Usuario;
import com.oficina.gestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        // 1. Validação de e-mail duplicado
        if (repository.findByEmail(usuario.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: E-mail já cadastrado.");
        }
        
        // 2. Garante a criptografia (Fundamental para evitar o erro 401 no login)
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        
        // 3. Define um tipo padrão caso venha nulo (Ex: CLIENTE)
        if (usuario.getTipo() == null) {
            // usuario.setTipo(TipoUsuario.CLIENTE); 
        }

        Usuario salvo = repository.save(usuario);
        salvo.setSenha(null); 
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Se não houver autenticação válida, retorna 401 imediatamente
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }

        String email = auth.getName(); 
        Usuario usuario = repository.findByEmail(email);
        
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil não encontrado.");
        }
        
        usuario.setSenha(null); 
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        List<Usuario> lista = repository.findAll();
        lista.forEach(u -> u.setSenha(null));
        return lista;
    }

    @GetMapping("/tipo/{tipo}")
    public List<Usuario> listarPorTipo(@PathVariable String tipo) {
        return repository.findAll().stream()
                .filter(u -> u.getTipo() != null && u.getTipo().name().equalsIgnoreCase(tipo))
                .peek(u -> u.setSenha(null))
                .toList();
    }
}
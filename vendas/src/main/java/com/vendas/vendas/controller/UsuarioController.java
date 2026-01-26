package com.vendas.vendas.controller;

import com.vendas.vendas.model.Usuario;
import com.vendas.vendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")  // ✅ SEM allowCredentials
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        System.out.println("📋 Usuários carregados: " + usuarios.size());
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepo.findById(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Usuario>> getClientes() {
        List<Usuario> clientes = usuarioRepo.findClientesComDados();
        System.out.println("📋 Clientes carregados: " + clientes.size());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/perfil/{perfil}")
    public ResponseEntity<List<Usuario>> getUsuariosByPerfil(@PathVariable String perfil) {
        List<Usuario> usuarios = usuarioRepo.findByPerfil(perfil);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario saved = usuarioRepo.save(usuario);
            System.out.println("✅ Usuário criado: " + saved.getUsername());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            System.out.println("❌ Erro criar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuarioDetails.getUsername() != null) usuario.setUsername(usuarioDetails.getUsername());
            if (usuarioDetails.getPassword() != null) usuario.setPassword(usuarioDetails.getPassword());
            if (usuarioDetails.getPerfil() != null) usuario.setPerfil(usuarioDetails.getPerfil());
            
            Usuario updated = usuarioRepo.save(usuario);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioRepo.existsById(id)) {
            usuarioRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

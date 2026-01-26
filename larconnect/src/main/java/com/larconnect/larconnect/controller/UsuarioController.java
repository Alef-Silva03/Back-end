package com.larconnect.larconnect.controller;

import com.larconnect.larconnect.dto.UsuarioDTO;
import com.larconnect.larconnect.model.Usuario;
import com.larconnect.larconnect.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody @Valid UsuarioDTO dto) {
        Usuario novoUsuario = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}
package com.venda.venda.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/protegido")
    public Map<String, String> testeProtegido(Authentication auth) {
        return Map.of(
            "mensagem", "API PROTEGIDA OK!",
            "usuario", auth.getName(),
            "perfil", auth.getAuthorities().iterator().next().getAuthority()
        );
    }
}

package com.sistema.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/cadastro")
    public String cadastroForm() { return "cadastro"; }
}

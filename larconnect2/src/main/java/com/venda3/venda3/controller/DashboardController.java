package com.venda3.venda3.controller;

import com.venda3.venda3.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping("/perfil")
    public ResponseEntity<?> getDadosPerfil(HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        
        if (logado == null) {
            return ResponseEntity.status(401).body("Não autorizado");
        }

        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", logado.getNome());
        dados.put("email", logado.getEmail());
        dados.put("senha", logado.getSenha());
        //dados.put("foto", logado.getFoto());
        dados.put("perfil", logado.getPerfil());
        dados.put("id", logado.getId());

        return ResponseEntity.ok(dados);
    }
}
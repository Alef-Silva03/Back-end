package com.projeto.larconnect.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.larconnect.model.Usuario;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {

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
		dados.put("cpf", logado.getCpf());
		dados.put("telefone", logado.getTelefone());
		dados.put("apartamento", logado.getApartamento());
		dados.put("perfil", logado.getPerfil());
		dados.put("id", logado.getId());

		return ResponseEntity.ok(dados);
	}
}
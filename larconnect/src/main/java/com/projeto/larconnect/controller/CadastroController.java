package com.projeto.larconnect.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.projeto.larconnect.model.Morador;
import com.projeto.larconnect.model.Perfil;
import com.projeto.larconnect.model.Usuario;
import com.projeto.larconnect.repository.MoradorRepository;
import com.projeto.larconnect.repository.UsuarioRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class CadastroController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MoradorRepository moradorRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// ================= PERFIL =================
	@GetMapping("/perfil")
	public ResponseEntity<?> getPerfil(HttpSession session) {

		String perfil = (String) session.getAttribute("perfil");
		String email = (String) session.getAttribute("emailUsuario");

		if (perfil == null || email == null) {
			return ResponseEntity.status(401).body(Map.of("message", "Faça login novamente"));
		}

		return ResponseEntity.ok(Map.of("perfil", perfil.toUpperCase(), "email", email, "autenticado", true));
	}

	// ================= CADASTRO =================
	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody Map<String, String> dados) {

		try {

			// ===== EXTRAÇÃO =====
			String nome = dados.get("nome");
			String email = dados.get("email");
			String senha = dados.get("senha");
			String cpf = dados.get("cpf");
			String telefone = dados.get("telefone");
			String apartamento = dados.get("apartamento");
			String perfilStr = dados.get("perfil");

			// ===== VALIDAÇÕES =====
			if (nome == null || nome.isBlank())
				return ResponseEntity.badRequest().body(Map.of("message", "Nome é obrigatório"));

			if (email == null || email.isBlank())
				return ResponseEntity.badRequest().body(Map.of("message", "Email é obrigatório"));

			if (senha == null || senha.length() < 6)
				return ResponseEntity.badRequest().body(Map.of("message", "Senha deve ter pelo menos 6 caracteres"));

			if (cpf == null || cpf.isBlank())
				return ResponseEntity.badRequest().body(Map.of("message", "CPF é obrigatório"));

			if (telefone == null || telefone.isBlank())
				return ResponseEntity.badRequest().body(Map.of("message", "Telefone é obrigatório"));

			if (apartamento == null || apartamento.isBlank())
				return ResponseEntity.badRequest().body(Map.of("message", "Apartamento é obrigatório"));

			if (perfilStr == null)
				return ResponseEntity.badRequest().body(Map.of("message", "Perfil é obrigatório"));

			// ===== CONVERTE ENUM =====
			Perfil perfil;
			try {
				perfil = Perfil.valueOf(perfilStr.toUpperCase());
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(Map.of("message", "Perfil inválido"));
			}

			// ===== EMAIL DUPLICADO =====
			if (usuarioRepository.findByEmailIgnoreCase(email.trim()).isPresent()) {
				return ResponseEntity.badRequest().body(Map.of("message", "Email já cadastrado"));
			}

			// ===== CRIPTOGRAFAR SENHA =====
			String senhaCriptografada = passwordEncoder.encode(senha);

			// ===== SE FOR MORADOR =====
			if (perfil == Perfil.INQUILINO || perfil == Perfil.PROPRIETARIO) {

				Morador morador = new Morador();

				morador.setNome(nome.trim());
				morador.setEmail(email.trim());
				morador.setSenha(senhaCriptografada);
				morador.setCpf(cpf.trim());
				morador.setTelefone(telefone.trim());
				morador.setApartamento(apartamento.trim());
				morador.setPerfil(perfil);

				moradorRepository.save(morador);

			} else {

				Usuario usuario = new Usuario();

				usuario.setNome(nome.trim());
				usuario.setEmail(email.trim());
				usuario.setSenha(senhaCriptografada);
				usuario.setCpf(cpf.trim());
				usuario.setTelefone(telefone.trim());
				usuario.setApartamento(apartamento.trim());
				usuario.setPerfil(perfil);

				usuarioRepository.save(usuario);
			}

			return ResponseEntity
					.ok(Map.of("message", "Cadastro realizado com sucesso!", "email", email, "perfil", perfil.name()));

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(500).body(Map.of("message", "Erro interno: " + e.getMessage()));
		}
	}
}

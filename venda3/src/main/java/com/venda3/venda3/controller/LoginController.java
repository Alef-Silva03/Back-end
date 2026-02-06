package com.venda3.venda3.controller;

import com.venda3.venda3.model.Usuario;
import com.venda3.venda3.model.Cliente;
import com.venda3.venda3.repository.UsuarioRepository;
import com.venda3.venda3.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> dados, HttpSession session) {
		String email = dados.get("email");
		String senhaPura = dados.get("senha");

		Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario != null && passwordEncoder.matches(senhaPura, usuario.getSenha())) {
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), null,
					Collections.emptyList());

			SecurityContextHolder.getContext().setAuthentication(authentication);
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			session.setAttribute("usuarioLogado", usuario);

			return ResponseEntity.ok(usuario);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
	}

	@PostMapping("/cadastro")
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody Map<String, String> dados) {
		String email = dados.get("email");
		if (usuarioRepository.existsByEmail(email)) {
			return ResponseEntity.badRequest().body("E-mail já cadastrado.");
		}

		Usuario usuario = new Usuario();
		usuario.setNome(dados.get("nome"));
		usuario.setEmail(email);
		usuario.setPerfil(dados.get("perfil").toUpperCase());
		usuario.setSenha(passwordEncoder.encode(dados.get("senha")));

		Usuario salvo = usuarioRepository.save(usuario);

		if ("CLIENTE".equalsIgnoreCase(dados.get("perfil"))) {
			Cliente cliente = new Cliente();
			cliente.setUsuario(salvo);
			clienteRepository.save(cliente);
		}
		return ResponseEntity.ok(salvo);
	}

	

	@PostMapping("/resetar-senha")
	@Transactional
	public ResponseEntity<?> resetarSenha(@RequestBody Map<String, String> dados) {
		String token = dados.get("token");
		String novaSenha = dados.get("novaSenha");

		Usuario usuario = usuarioRepository.findByResetToken(token);

		if (usuario != null && usuario.getTokenExpiration() != null
				&& usuario.getTokenExpiration().isAfter(LocalDateTime.now())) {

			usuario.setSenha(passwordEncoder.encode(novaSenha));
			usuario.setResetToken(null);
			usuario.setTokenExpiration(null);
			usuarioRepository.save(usuario);

			return ResponseEntity.ok("Senha alterada com sucesso!");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
	}

	@PostMapping("/esqueci-senha")
	@Transactional
	public ResponseEntity<?> esqueciSenha(@RequestBody Map<String, String> dados) {
	    String email = dados.get("email");
	    Usuario usuario = usuarioRepository.findByEmail(email);

	    if (usuario == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado.");
	    }

	    // 1. Gera o token
	    String token = UUID.randomUUID().toString();
	    usuario.setResetToken(token);
	    usuario.setTokenExpiration(LocalDateTime.now().plusMinutes(15));
	    usuarioRepository.save(usuario);

	    // 2. CRIA O LINK AUTOMÁTICO (Simulando o que iria no e-mail)
	    String linkDeRecuperacao = "http://localhost:8080/confirmar-nova-senha.html?token=" + token;

	    // 3. EXIBE NO CONSOLE PARA O ALUNO CLICAR
	    System.out.println("\n--- RECUPERAÇÃO DE SENHA ---");
	    System.out.println("Usuário: " + email);
	    System.out.println("Clique aqui: " + linkDeRecuperacao);
	    System.out.println("----------------------------\n");

	    return ResponseEntity.ok(Map.of("message", "Link enviado! Verifique o console do seu Java (STS/Eclipse)."));
	}
}
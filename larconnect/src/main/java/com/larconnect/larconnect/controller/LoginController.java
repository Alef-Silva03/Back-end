package com.larconnect.larconnect.controller;

import com.larconnect.larconnect.model.Usuario;
import com.larconnect.larconnect.model.Funcionario;
import com.larconnect.larconnect.model.Inquilino;
import com.larconnect.larconnect.model.Proprietario;
import com.larconnect.larconnect.model.Sindico;
import com.larconnect.larconnect.repository.ProprietarioRepository;
import com.larconnect.larconnect.repository.SindicoRepository;
import com.larconnect.larconnect.repository.UsuarioRepository;
import com.larconnect.larconnect.repository.FuncionarioRepository;
import com.larconnect.larconnect.repository.InquilinoRepository;
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

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ProprietarioRepository proprietarioRepository;
	
	@Autowired
	private InquilinoRepository inquilinoRepository;
	
	@Autowired
	private SindicoRepository sindicoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> dados, HttpSession session) {
		String email = dados.get("email");
		String senhaPura = dados.get("senha");

		Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario != null && passwordEncoder.matches(senhaPura, usuario.getSenha())) {

			// --- PASSO CRUCIAL PARA O REDIRECIONAMENTO FUNCIONAR ---
			// Criamos um token de autenticação reconhecido pelo Spring Security
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), null,
					Collections.emptyList() // Aqui poderiam ir as roles/perfil
			);

			// Definimos essa autenticação no contexto global de segurança
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Vinculamos o contexto de segurança à sessão atual
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			// -------------------------------------------------------

			session.setAttribute("usuarioLogado", usuario);
			session.setAttribute("perfil", usuario.getPerfil());

			return ResponseEntity.ok(usuario);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
	}

	@PostMapping("/cadastro")
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody Map<String, String> dados) {
		try {
			String nome = dados.get("nome");
			String email = dados.get("email");			
			String senha = dados.get("senha");
			String cpf = dados.get("cpf");
			String telefone = dados.get("telefone");
			String apartamento = dados.get("apartamento");
			String perfil = dados.get("perfil");

			if (usuarioRepository.existsByEmail(email)) {
				return ResponseEntity.badRequest().body("Este e-mail já está cadastrado.");
			}

			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setPerfil(perfil.toUpperCase());
			usuario.setSenha(passwordEncoder.encode(senha));

			Usuario usuarioSalvo = usuarioRepository.save(usuario);

			if ("PROPRIETARIO".equalsIgnoreCase(perfil)) {
				Proprietario proprietario = new Proprietario();
				proprietario.setUsuario(usuarioSalvo);
				proprietarioRepository.save(proprietario);

			} else if ("INQUILINO".equalsIgnoreCase(perfil)) {
				Inquilino inquilino = new Inquilino();
				inquilino.setUsuario(usuarioSalvo);
				inquilinoRepository.save(inquilino);

			} else if ("SINDICO".equalsIgnoreCase(perfil)) {
				Sindico sindico = new Sindico();
				sindico.setUsuario(usuarioSalvo);
				sindicoRepository.save(sindico);

			} else {
				Funcionario funcionario = new Funcionario();
				funcionario.setUsuario(usuarioSalvo);
				funcionarioRepository.save(funcionario);

			}

			return ResponseEntity.ok(usuarioSalvo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao processar cadastro: " + e.getMessage());
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		SecurityContextHolder.clearContext(); // Limpa a segurança
		session.invalidate(); // Mata a sessão
		return ResponseEntity.ok("Sessão encerrada.");
	}
}
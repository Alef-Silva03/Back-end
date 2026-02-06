package com.projeto.larconnect.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.projeto.larconnect.model.Perfil;
import com.projeto.larconnect.model.Usuario;
import com.projeto.larconnect.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

		Perfil perfil = usuario.getPerfil() != null ? usuario.getPerfil() : Perfil.FUNCIONARIO;

		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));

		return User.builder().username(usuario.getEmail()).password(usuario.getSenha()).authorities(authorities)
				.accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build();
	}

}

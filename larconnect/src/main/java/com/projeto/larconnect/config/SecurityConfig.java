package com.projeto.larconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// Páginas públicas
				.requestMatchers("/login.html", "/cadastro.html", "/esqueci-senha.html", "/confirmar-nova-senha.html")
				.permitAll()

				// API de autenticação
				.requestMatchers("/api/auth/**").permitAll()

				// Arquivos estáticos
				.requestMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()

				// Qualquer outra rota precisa login
				.anyRequest().authenticated())
				// Configura a política de sessão para permitir login manual via Controller
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.formLogin(form -> form.loginPage("/login.html").permitAll())
				.logout(logout -> logout.logoutUrl("/api/auth/logout").logoutSuccessUrl("/login.html")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll())
				.exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
					// Redireciona para o login se tentar acessar área restrita
					response.sendRedirect("/login.html");
				}));

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
package com.oficina.gestao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/index.html", 
                    "/login.html", 
                    "/cadastro.html", 
                    "/minha_area_cliente.html", 
                    "/dashboard_admin.html", // ADICIONADO: Libera a visualização da página
                    "/api/usuarios/cadastrar", 
                    "/css/**", 
                    "/js/**", 
                    "/img/**",
                    "/favicon.ico"
                ).permitAll()
                // A segurança agora foca em proteger os DADOS (os endpoints /api)
                .requestMatchers("/api/usuarios/me").authenticated()
                .requestMatchers("/api/usuarios").hasAuthority("ADMINISTRADOR") // Opcional: proteção extra
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .httpBasic(Customizer.withDefaults())
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpStatus.OK.value()))
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
package com.venda3.venda3.config;

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
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // 1. Libera páginas HTML públicas (Login e Esqueci Senha)
              
                .requestMatchers("/login.html", "/esqueci-senha.html", "/confirmar-nova-senha.html", "/api/auth/**").permitAll()
                
                // 2. Libera toda a API de autenticação (Login, Cadastro, Reset de Senha)
                .requestMatchers("/api/auth/**").permitAll()
                
                // 3. Libera recursos estáticos
                .requestMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
                
                // 4. Qualquer outra requisição (como dashboard.html) exige autenticação
                .anyRequest().authenticated()
            )
            // Configura a política de sessão para permitir login manual via Controller
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/login.html")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    // Redireciona para o login se tentar acessar área restrita
                    response.sendRedirect("/login.html");
                })
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
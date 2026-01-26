package com.vendasfinal.sistema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilitado para facilitar o desenvolvimento local
            .authorizeHttpRequests(auth -> auth
                // Libera as rotas públicas: cadastro, login e recursos estáticos
                .requestMatchers("/login", "/cadastro", "/usuarios/salvar", "/esqueci-senha", "/resetar-senha").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                
                // Restringe acessos por papel (Role)
                // O Spring Security espera "ROLE_ADMIN" se usar hasRole, ou "ADMIN" se usar hasAuthority
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/cliente/**").hasAuthority("CLIENTE")
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username") // Alinhado com o name="username" no login.html
                .passwordParameter("password") // Alinhado com o name="password" no login.html
                .defaultSuccessUrl("/home", true) 
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        
        return http.build();
    }
}
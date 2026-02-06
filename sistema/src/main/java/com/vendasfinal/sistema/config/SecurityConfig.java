package com.vendasfinal.sistema.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomLoginSuccessHandler successHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**", "/usuarios/salvar")
            )
            .authorizeHttpRequests(auth -> auth
                // 1. RECURSOS ESTÁTICOS
                .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()

                // 2. O ADMIN É PROIBIDO NA VITRINE (Acesso Negado se tentar entrar na loja)
                // Usamos o acesso exclusivo para CLIENTE ou Usuário não logado
                .requestMatchers("/loja/**").hasAuthority("CLIENTE") 
                
                // 3. O CLIENTE É PROIBIDO NO ADMIN
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                
                // 4. ROTAS PÚBLICAS (Apenas Login e Cadastro básico)
                .requestMatchers("/", "/login", "/cadastro", "/usuarios/salvar", "/esqueci-senha", "/resetar-senha").permitAll()
                
                // 5. QUALQUER OUTRA ROTA (Exige estar logado)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler) // Crucial: Admin -> Dashboard / Cliente -> Loja
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true") // Volta para o login após sair
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            )
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        
        return http.build();
    }
}
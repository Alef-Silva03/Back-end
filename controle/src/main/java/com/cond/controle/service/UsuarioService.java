package com.cond.controle.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cond.controle.model.Usuario;
import com.cond.controle.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Regra de Negócio: Antes de salvar, a palavra-passe é criptografada.
     * Isso impede que administradores ou invasores leiam os dados sensíveis.
     */
    public Usuario salvar(Usuario usuario) {
        // Criptografa a senha usando BCrypt
        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        
        // Salva no MySQL via Hibernate
        return repository.save(usuario);
    }

    /**
     * Lista todos os usuários cadastrados no condomínio.
     */
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    /**
     * Busca um usuário por ID (útil para edição de perfil).
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Lógica de Autenticação (Login) solicitada no documento.
     * Verifica se o e-mail existe e se a senha "bate" com a criptografia.
     */
    public Usuario autenticar(String email, String senhaPura) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Compara a senha digitada com a senha criptografada no banco
            if (encoder.matches(senhaPura, usuario.getSenha())) {
                return usuario; // Login sucesso
            }
        }
        return null; // Login falhou
    }

    /**
     * Remove um utilizador (ex: morador que se mudou).
     */
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
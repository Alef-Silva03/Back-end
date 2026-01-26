package com.venda3.venda3.service;

import com.venda3.venda3.dto.UsuarioDTO;
import com.venda3.venda3.model.Usuario;
import com.venda3.venda3.repository.UsuarioRepository;
import com.venda3.venda3.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Usuario salvar(UsuarioDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new BusinessException("Este E-mail já está em uso.");
        }
        if (repository.existsByCpf(dto.cpf())) {
            throw new BusinessException("Este CPF já está cadastrado.");
        }

        Usuario usuario = new Usuario();
		// Mapeamento manual (ou use MapStruct/ModelMapper em projetos grandes)
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setCpf(dto.cpf());
        usuario.setTelefone(dto.telefone());
        usuario.setApartamento(dto.apartamento());
        usuario.setPerfil(dto.perfil());
        return repository.save(usuario);
    }
}
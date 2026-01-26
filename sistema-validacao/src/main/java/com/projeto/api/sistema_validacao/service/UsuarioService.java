package com.projeto.api.sistema_validacao.service;

import com.projeto.api.sistema_validacao.dto.UsuarioDTO;
import com.projeto.api.sistema_validacao.model.Usuario;
import com.projeto.api.sistema_validacao.repository.UsuarioRepository;
import com.projeto.api.sistema_validacao.exception.BusinessException;
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
        usuario.setCpf(dto.cpf());
        usuario.setTelefone(dto.telefone());
        usuario.setCep(dto.cep());
        usuario.setLogradouro(dto.logradouro());
        usuario.setBairro(dto.bairro());
        usuario.setCidade(dto.cidade());
        usuario.setEstado(dto.estado());
        usuario.setNumero(dto.numero());
        usuario.setComplemento(dto.complemento());

        return repository.save(usuario);
    }
}
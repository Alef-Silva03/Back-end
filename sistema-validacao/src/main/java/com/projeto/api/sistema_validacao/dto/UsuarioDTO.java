package com.projeto.api.sistema_validacao.dto;

import jakarta.validation.constraints.*;

public record UsuarioDTO(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    String email,

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas os 11 números")
    String cpf,

    @NotBlank(message = "Telefone é obrigatório")
    String telefone,

    @NotBlank(message = "CEP é obrigatório")
    String cep,

    @NotBlank(message = "Logradouro é obrigatório")
    String logradouro,

    @NotBlank(message = "Bairro é obrigatório")
    String bairro,

    @NotBlank(message = "Cidade é obrigatória")
    String cidade,

    @NotBlank(message = "Estado é obrigatório")
    String estado,

    @NotBlank(message = "Número é obrigatório")
    String numero,

    String complemento // Opcional
) {}
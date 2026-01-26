package com.venda3.venda3.dto;

import jakarta.validation.constraints.*;

public record UsuarioDTO(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    String email,
    
    @NotBlank(message = "Número é obrigatório")
    String senha,

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas os 11 números")
    String cpf,

    @NotBlank(message = "Telefone é obrigatório")
    String telefone,

    @NotBlank(message = "Número é obrigatório")
    String apartamento,

    @NotBlank(message = "Número é obrigatório")
    String perfil
) {}
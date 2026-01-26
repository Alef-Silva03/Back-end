package com.vendas.vendas.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cliente")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcliente")
	private Long idcliente;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cpf", unique = true)
	private String cpf;

	@Column(name = "email")
	private String email;

	@Column(name = "telefone")
	private String telefone;

	// ✅ SEM @OneToOne(mappedBy = "usuario") - RELAÇÃO UNIDIRECIONAL

	// Getters e Setters
	public Long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Long idcliente) {
		this.idcliente = idcliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}

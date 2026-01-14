package com.exerc.exerc.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcliente")
	private long idcliente;

	@Column(name = "nomecliente", length = 100, nullable = false)
	private String nomecliente;

	@Column(name = "cpf", length = 14, nullable = false)
	private String cpf;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	// Construtores
	public Cliente() {
	}

	public Cliente(String nomecliente, String cpf, String email) {
		this.nomecliente = nomecliente;
		this.cpf = cpf;
		this.email = email;
	}
	// Getters e Setters (encapsulamento OO)

	public long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(long idcliente) {
		this.idcliente = idcliente;
	}

	public String getNomecliente() {
		return nomecliente;
	}

	public void setNomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
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

	

}

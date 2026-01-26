package com.vendas.vendas.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario")
	private Long idusuario;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "perfil", nullable = false, length = 20)
	private String perfil;

	@Column(name = "idcliente") // ✅ FK SIMPLES (sem @OneToOne)
	private Long idcliente;

	// Getters e Setters
	public Long getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Long idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Long idcliente) {
		this.idcliente = idcliente;
	}
}

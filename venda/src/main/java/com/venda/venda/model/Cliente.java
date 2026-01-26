package com.venda.venda.model;


import jakarta.persistence.*;
@Entity
@Table(name="cliente")
public class Cliente {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=40)
    private String nome;
    @Column(unique=true, length=14)
    private String cpf;
    @Column(unique=true, length=30)
    private String email;
    // getters/setters/toString
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}

package com.oficina.gestao.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double precoMaoDeObra;
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
	public Double getPrecoMaoDeObra() {
		return precoMaoDeObra;
	}
	public void setPrecoMaoDeObra(Double precoMaoDeObra) {
		this.precoMaoDeObra = precoMaoDeObra;
	}
    
}
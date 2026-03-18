package com.cond.controle.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private Double valor;
    private boolean aprovado;

    @Enumerated(EnumType.STRING) // IMPORTANTE: Isso evita o erro de truncamento
    private TipoAnuncio tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario anunciante;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public TipoAnuncio getTipo() {
		return tipo;
	}

	public void setTipo(TipoAnuncio tipo) {
		this.tipo = tipo;
	}

	public Usuario getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(Usuario anunciante) {
		this.anunciante = anunciante;
	}
    
    
}
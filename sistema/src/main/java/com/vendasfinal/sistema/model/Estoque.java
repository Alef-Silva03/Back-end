package com.vendasfinal.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

//Entidade Estoque
@Entity
@Table(name = "estoque")
@Data
public
class Estoque {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @OneToOne
 @JoinColumn(name = "produto_id")
 private Produto produto;

 private Integer quantidade;

 public Long getId() {
	return id;
 }

 public void setId(Long id) {
	this.id = id;
 }

 public Produto getProduto() {
	return produto;
 }

 public void setProduto(Produto produto) {
	this.produto = produto;
 }

 public Integer getQuantidade() {
	return quantidade;
 }

 public void setQuantidade(Integer quantidade) {
	this.quantidade = quantidade;
 }


 
 
}
package com.projeto.larconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "morador")
@PrimaryKeyJoinColumn(name = "id")
public class Morador extends Usuario {

	// Getters e Setters

}

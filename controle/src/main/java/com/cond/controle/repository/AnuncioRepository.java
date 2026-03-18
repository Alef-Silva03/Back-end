package com.cond.controle.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cond.controle.model.Anuncio;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    // O Spring Data JPA mapeia automaticamente:
    // "findBy" (Select) + "Aprovado" (Atributo no Model) + "True/False" (Valor booleano)
    
    List<Anuncio> findByAprovadoTrue();  
    
    List<Anuncio> findByAprovadoFalse(); 
}
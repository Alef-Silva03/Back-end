package com.projeto.larconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projeto.larconnect.model.Morador;

import java.util.Optional;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

    Optional<Morador> findByEmail(String email);

}
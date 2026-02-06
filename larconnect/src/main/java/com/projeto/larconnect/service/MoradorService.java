package com.projeto.larconnect.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.projeto.larconnect.model.Morador;
import com.projeto.larconnect.repository.MoradorRepository;

@Service
public class MoradorService {

	private final MoradorRepository moradorRepository;

	public MoradorService(MoradorRepository moradorRepository) {
		this.moradorRepository = moradorRepository;
	}

	public List<Morador> listAll() {
		return moradorRepository.findAll();
	}

	public Morador findById(Long id) {
		return moradorRepository.findById(id).orElseThrow(() -> new RuntimeException("Morador não encontrado"));
	}

	public Morador save(Morador morador) {
		return moradorRepository.save(morador);
	}

	public void deleteById(Long id) {
		moradorRepository.deleteById(id);
	}
}

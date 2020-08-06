package com.b2w.api.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.b2w.api.models.Planeta;
import com.b2w.api.repositories.PlanetaRepository;
import com.b2w.api.exception.EntidadeNaoEncontradaException;

@Service
public class CadastroPlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;
	
		
	public Planeta salvar(Planeta planeta) {
		return planetaRepository.save(planeta);
	}

	public void excluir(String id) {
		try {
			planetaRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Nao planeta com o id \"%s\" nao existe", id));
		}
	}

}

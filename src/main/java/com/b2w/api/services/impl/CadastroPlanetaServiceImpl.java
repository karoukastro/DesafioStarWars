package com.b2w.api.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.b2w.api.models.Planeta;
import com.b2w.api.repositories.PlanetaRepository;
import com.b2w.api.services.CadastroPlanetaService;
import com.b2w.api.exception.EntidadeNaoEncontradaException;

@Service
public class CadastroPlanetaServiceImpl implements CadastroPlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Override
	public List<Planeta> listarTodos() {
		return planetaRepository.findAll();
	}

	@Override
	public Planeta listarPorId(String id) {
		Optional<Planeta> returnedPlaneta = planetaRepository.findById(id);
		if( returnedPlaneta.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("O planeta com o id \"%s\" nao existe.", id));
		}
		Planeta planeta = returnedPlaneta.get();
		return planeta;
	}

	@Override
	public Planeta listarPorNome(String nome) {
		Planeta planeta = planetaRepository.findByNome(nome);
		if (planeta == null) {
			throw new EntidadeNaoEncontradaException(String.format("O planeta com o nome \"%s\" nao existe.", nome));
		}
		return planeta;
	}
	
	@Override
	public Planeta adicionar(Planeta planeta) {
		Planeta planetaRetornado = planetaRepository.findByNome(planeta.getNome());
		if (planetaRetornado != null) {
			throw new EntidadeNaoEncontradaException(String.format("O planeta com o nome \"%s\" ja existe cadastrado.", planeta.getNome()));
		}
		return planetaRepository.save(planeta);
	}

	@Override
	public Planeta modificar(String id, Planeta planeta) {
		Optional<Planeta> returnedPlaneta = planetaRepository.findById(id);
		
		if( returnedPlaneta.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Nao planeta com o id \"%s\" nao existe", id));
		}
		planeta.setId(id);
		return planetaRepository.save(planeta);
	}

	@Override
	public void remover(String id) {
		Optional<Planeta> planeta = planetaRepository.findById(id);
		
		if( planeta.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Nao planeta com o id \"%s\" nao existe", id));
		}else {
			planetaRepository.deleteById(id);
		}		
	}
	
	@Override
	public long getValues(){
		return planetaRepository.count();
	}

}

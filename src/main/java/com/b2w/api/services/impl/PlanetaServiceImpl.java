package com.b2w.api.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.b2w.api.models.Planeta;
import com.b2w.api.repositories.PlanetaRepository;
import com.b2w.api.services.PlanetaService;

@Service
public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Override
	public List<Planeta> listarTodos() {
		return this.planetaRepository.findAll();
	}

	@Override
	public Optional<Planeta> listarPorId(String id) {
		return this.planetaRepository.findById(id);
	}

	@Override
	public Planeta listarPorNome(String nome) {
		return this.planetaRepository.findByNome(nome);
	}
	
	@Override
	public Planeta adicionar(Planeta planeta) {
		if(this.planetaRepository.findByNome(planeta.getNome()) == null) {
			return this.planetaRepository.save(planeta);
		}else {
			return null;
		}
	}

	@Override
	public Planeta modificar(Planeta planeta) {
		if(this.planetaRepository.findByNome(planeta.getNome()) == null) {
			return this.planetaRepository.save(planeta);
		}else {
			return null;
		}
	}

	@Override
	public void deletar(String id) {
		this.planetaRepository.deleteById(id);
	}
	
	@Override
	public long getValues(){
		return this.planetaRepository.count();
	}

}

package com.b2w.api.services;

import java.util.List;
import java.util.Optional;

import com.b2w.api.models.Planeta;

public interface PlanetaService {

	List<Planeta> listarTodos();
	
	Optional<Planeta> listarPorId(String id);
	Planeta listarPorNome(String nome);
	Planeta adicionar(Planeta planeta);
	Planeta modificar(Planeta planeta);
	void deletar(String id);
	long getValues();
	
}

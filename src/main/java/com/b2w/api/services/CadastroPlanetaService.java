package com.b2w.api.services;

import java.util.List;
import java.util.Optional;

import com.b2w.api.models.Planeta;

public interface CadastroPlanetaService {

	List<Planeta> listarTodos();
	
	Planeta listarPorId(String id);
	Planeta listarPorNome(String nome);
	Planeta adicionar(Planeta planeta);
	Planeta modificar(String id, Planeta planeta);
	void remover(String id);
	long getValues();
	
}

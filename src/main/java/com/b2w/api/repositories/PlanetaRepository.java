package com.b2w.api.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.b2w.api.models.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	List<Planeta> findByNomeContaining(String nome);
	
	Planeta findByNome(String nome);
}

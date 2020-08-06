package com.b2w.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.b2w.api.models.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	Planeta findByNome(String nome);
}

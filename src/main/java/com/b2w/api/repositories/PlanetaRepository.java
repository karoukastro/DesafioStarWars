package com.b2w.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.b2w.api.models.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	List<Planeta> findByNomeContaining(String nome);
	Optional<Planeta> findById(String id);
	Planeta findByNome(String nome);
}

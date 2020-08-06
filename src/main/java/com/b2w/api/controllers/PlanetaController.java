package com.b2w.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.b2w.api.models.Planeta;
import com.b2w.api.repositories.PlanetaRepository;
import com.b2w.api.responses.CountResponse;
import com.b2w.api.responses.PlanetaResponse;
import com.b2w.api.services.impl.CadastroPlanetaService;
import com.b2w.api.util.SWApi;
import com.b2w.api.exception.EntidadeNaoEncontradaException;

@RestController
@RequestMapping(path = "/api/planetas")
public class PlanetaController {
		
	@Autowired
	private CadastroPlanetaService cadastroPlaneta;
	
	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Autowired
	private SWApi swapi;
	
	@GetMapping
	public ResponseEntity<CountResponse<List<PlanetaResponse>>> listarTodos(){
		List<Planeta> planetasRetornados = planetaRepository.findAll();
		return ResponseEntity.ok(new CountResponse<List<PlanetaResponse>>(planetasRetornados.size(), insereAparicoes(planetasRetornados)));
	}
	
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable String id){
			Optional<Planeta> planeta = planetaRepository.findById(id);
		
			if(planeta.isPresent()) {
				return ResponseEntity
					.ok(new PlanetaResponse(planeta.get(), swapi.verificarAparicoes(planeta.get().getNome())));
			}

			return ResponseEntity.notFound().build();
		
	}
	
	@GetMapping(path = "/nome/{nome}")
	public ResponseEntity<?> listarPorNome(@PathVariable String nome){
			Planeta planeta =  planetaRepository.findByNome(nome);
			
			if(planeta != null) {
				return ResponseEntity
					.ok(new PlanetaResponse(planeta, swapi.verificarAparicoes(planeta.getNome())));
			}
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public Planeta cadastrar(@Valid @RequestBody Planeta planeta){
			return planeta = cadastroPlaneta.salvar(planeta);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> modificar(@PathVariable String id, @RequestBody Planeta planeta){

		Optional<Planeta> planetaAtual = planetaRepository.findById(id);
				
		if( planetaAtual.isPresent()) {
			BeanUtils.copyProperties(planeta, planetaAtual.get(), "id");
			Planeta planetaSalvo = cadastroPlaneta.salvar(planetaAtual.get());
			return ResponseEntity.ok(
					new PlanetaResponse(planetaSalvo, swapi.verificarAparicoes(planetaSalvo.getNome())));
		}
		
		return ResponseEntity.notFound().build();	
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> remover(@PathVariable String id){
		try {
			cadastroPlaneta.excluir(id);
		
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	/*
	 * Recebe uma Lista de Planetas, insere a quantidade de vezes que o planeta pesquisado apareceu em filmes do Star Wars e retorna a resposta
	 */
	public List<PlanetaResponse> insereAparicoes(List<Planeta> planetas) {
		List<PlanetaResponse> resposta = new ArrayList<>();
		for(Planeta p: planetas ) {
			resposta.add(new PlanetaResponse(p ,swapi.verificarAparicoes(p.getNome())));
		}
		return resposta;
	}
}

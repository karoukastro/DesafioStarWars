package com.b2w.api.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

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
import com.b2w.api.responses.CountResponse;
import com.b2w.api.responses.PlanetaResponse;
import com.b2w.api.services.impl.CadastroPlanetaServiceImpl;
import com.b2w.api.util.SWApi;
import com.b2w.api.exception.EntidadeNaoEncontradaException;

@RestController
@RequestMapping(path = "/api/planetas")
public class PlanetaController {
		
	@Autowired
	private CadastroPlanetaServiceImpl cadastroPlanetaService;
	
	@Autowired
	private SWApi swapi;
	
	@GetMapping
	public ResponseEntity<CountResponse<List<PlanetaResponse>>> listarTodos(){
		List<Planeta> planetasRetornados = cadastroPlanetaService.listarTodos();
		return ResponseEntity.ok(new CountResponse<List<PlanetaResponse>>(planetasRetornados.size(), insereAparicoes(planetasRetornados)));
	}
	
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable String id){
		try {
			Planeta planeta = cadastroPlanetaService.listarPorId(id);
		
			return ResponseEntity
					.ok(new PlanetaResponse(planeta, swapi.verificarAparicoes(planeta.getNome())));
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@GetMapping(path = "/nome/{nome}")
	public ResponseEntity<?> listarPorNome(@PathVariable String nome){
		try {
			Planeta planeta =  cadastroPlanetaService.listarPorNome(nome);
			return ResponseEntity
					.ok(new PlanetaResponse(planeta, swapi.verificarAparicoes(planeta.getNome())));
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@Valid @RequestBody Planeta planeta){
		try {
			planeta = cadastroPlanetaService.adicionar(planeta);
			return ResponseEntity
					.ok(new PlanetaResponse(planeta, swapi.verificarAparicoes(planeta.getNome())));
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
				.body(e.getMessage());
		}
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> modificar(@PathVariable String id, @RequestBody Planeta planeta){
		try {
			planeta = cadastroPlanetaService.modificar(id, planeta);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new PlanetaResponse(planeta, swapi.verificarAparicoes(planeta.getNome())));
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> remover(@PathVariable String id){
		try {
			cadastroPlanetaService.remover(id);
		
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

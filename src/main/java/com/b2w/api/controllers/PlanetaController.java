package com.b2w.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.b2w.api.responses.ErrorResponse;
import com.b2w.api.responses.MessageResponse;
import com.b2w.api.responses.PlanetaResponse;
import com.b2w.api.services.PlanetaService;
import com.b2w.api.util.SWApi;

@RestController
@RequestMapping(path = "/api/planetas")
public class PlanetaController {
	
	@Autowired
	private PlanetaService planetaService;
	
	@Autowired
	private SWApi swapi;
	
	@GetMapping
	public ResponseEntity<CountResponse<List<PlanetaResponse>>> listarTodos(){
		List<Planeta> planetasRetornados = this.planetaService.listarTodos();
		return ResponseEntity.ok(new CountResponse<List<PlanetaResponse>>(planetasRetornados.size(), insereAparicoes(planetasRetornados)));
	}
	
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable(name = "id") String id){
		Optional<Planeta> planetaOptional = this.planetaService.listarPorId(id);
		if(planetaOptional.isPresent()) {
			Planeta p = planetaOptional.get();
			return ResponseEntity.ok(new PlanetaResponse(p.getId(), p.getNome(), p.getClima(), p.getTerreno(), swapi.verificarAparicoes(p.getNome())));
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Planeta não encontrado"));
		}
	}
	
	@GetMapping(path = "/nome/{nome}")
	public ResponseEntity<?> listarPorNome(@PathVariable(name = "nome")  String nome){
		Planeta p =  this.planetaService.listarPorNome(nome);
		if(p != null) {
			return ResponseEntity.ok(new PlanetaResponse(p.getId(), p.getNome(), p.getClima(), p.getTerreno(), swapi.verificarAparicoes(p.getNome())));
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Planeta não encontrado"));
		}
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@Valid @RequestBody Planeta planeta, BindingResult resultado){
		if (resultado.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			resultado.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new ErrorResponse(erros));
		}
		Planeta p = this.planetaService.adicionar(planeta);
		if( p != null) {
			return ResponseEntity.ok(new PlanetaResponse(p.getId(), p.getNome(), p.getClima(), p.getTerreno()));
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Este Planeta já foi cadastrado anteriormente"));
		}
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> modificar(@PathVariable(name = "id") String id, @Valid @RequestBody Planeta planeta){
		if(this.planetaService.listarPorId(id).isPresent()) {
			planeta.setId(id);
			Planeta p = this.planetaService.modificar(planeta);
			if (p != null) {
				return ResponseEntity.ok(new PlanetaResponse(p.getId(), p.getNome(), p.getClima(), p.getTerreno(), swapi.verificarAparicoes(p.getNome())));
			}else {
				return ResponseEntity.badRequest().body(new MessageResponse("Já existe planeta cadastrado com esse nome"));
			}	
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Planeta não encontrado"));
		}

	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deletar(@PathVariable(name = "id") String id){
		Optional<Planeta> planetaOptional = this.planetaService.listarPorId(id);
		if(planetaOptional.isPresent()) {
			this.planetaService.deletar(id);
			return ResponseEntity.ok(new MessageResponse("O Planeta " + planetaOptional.get().getNome() + " foi deletado"));
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Planeta não encontrado"));
		}
	}
	
	/*
	 * Recebe uma Lista de Planetas, insere a quantidade de vezes que o planeta pesquisado apareceu em filmes do Star Wars e retorna a resposta
	 */
	public List<PlanetaResponse> insereAparicoes(List<Planeta> planetas) {
		List<PlanetaResponse> resposta = new ArrayList<>();
		for(Planeta p: planetas ) {
			resposta.add(new PlanetaResponse(p.getId(),p.getNome(),p.getClima(),p.getTerreno(),swapi.verificarAparicoes(p.getNome())));
		}
		return resposta;
	}
}

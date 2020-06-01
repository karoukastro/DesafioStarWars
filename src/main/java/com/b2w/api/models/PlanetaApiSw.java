package com.b2w.api.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Carolina Castro
 * 
 * Classe utilizada pra a receber o JSON vindo da API do Star Wars
 *
 */
public class PlanetaApiSw{

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("films")
	private List<String> films;
	
	public PlanetaApiSw() {
		
	}
	
	public PlanetaApiSw(String name, List<String> films) { 
		this.name = name;
		this.films = films;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}
	
	
}

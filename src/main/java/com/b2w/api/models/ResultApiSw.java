package com.b2w.api.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Carolina Castro
 * 
 * Classe utilizada pra a receber o JSON vindo da API do Star Wars
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultApiSw{
	
	@JsonProperty("results")
	private List<PlanetaApiSw> results;

	public ResultApiSw() {
		
	}
	public ResultApiSw(List<PlanetaApiSw> results) {
		this.results = results;
	}
	public List<PlanetaApiSw> getResults() {
		return results;
	}
	public void setResults(List<PlanetaApiSw> results) {
		this.results = results;
	}
	
	
	
	
}

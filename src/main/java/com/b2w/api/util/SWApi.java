package com.b2w.api.util;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.b2w.api.exception.ServiceUnavailable;
import com.b2w.api.models.Planeta;
import com.b2w.api.models.ResultApiSw;
import com.b2w.api.services.PlanetaService;

/**
 * 
 * @author Carolina Castro
 * 
 * Classe utilizada para requesição Get na API do Star Wars
 * 
 */
@Service
public class SWApi {
   	
	@Autowired
	private RestTemplate restTemplate;
	
	private String url = "https://swapi.dev/api/"; 
	
	@Autowired
	private PlanetaService planetaService;
	
	/*
	 * Recebe o nome do Planeta, pesquisa quantas vezes ele apareceu em filmes e retorna o valor
	 */
	public int verificarAparicoes(String nome) {
	Planeta planetasRetornado =  this.planetaService.listarPorNome(nome);
		if(planetasRetornado != null) {
			ResponseEntity<ResultApiSw> response = getAparicoes(nome);
			if(response.getBody().getResults().size()> 0 ) {
				return response.getBody().getResults().get(0).getFilms().size();
			}
		}
		return 0;
	}
		
	/*
	 * Consulta na API do Star Wars através do método GET, passando o nome do Planeta 
	 */
	public ResponseEntity<ResultApiSw> getAparicoes(String nome) {

		
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/planets/")
                .queryParam("search", nome)
                .build().toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<ResultApiSw> response = restTemplate.exchange(
					uri,
			        HttpMethod.GET, 
			        entity, 
			        ResultApiSw.class);
			
			return response;
		
		}catch(RestClientException e) {
			throw new ServiceUnavailable("A API do Star Wars está fora do ar");
		}
   	}
    
}

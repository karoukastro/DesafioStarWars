package com.b2w.api.responses;

/**
 * 
 * @author Carolina Castro
 * 
 * Classe para envelopar a resposta dos Planetas com suas respectivas aparições
 *
 */
public class PlanetaResponse {

	private String id;
	private String nome;
	private String clima;
	private String terreno;
	private int numAparicoes;
				
	public PlanetaResponse(String id, String nome, String clima, String terreno, int numAparicoes) {
		this.id = id;
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
		this.numAparicoes = numAparicoes;
	}
	
	public PlanetaResponse(String id, String nome, String clima, String terreno) {
		this.id = id;
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
		this.numAparicoes = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public int getNumAparicoes() {
		return numAparicoes;
	}

	public void setNumAparicoes(int numAparicoes) {
		this.numAparicoes = numAparicoes;
	}

			
	
	
	
}

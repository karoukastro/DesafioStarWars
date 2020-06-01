package com.b2w.api.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;

@Document
public class Planeta {

	@Id
	private String id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotEmpty(message = "Clima não pode ser vazio")
	private String clima;
	
	@NotEmpty(message = "Terreno não pode ser vazio")
	private String terreno;
	
	public Planeta(){
		
	}

	public Planeta(@NotEmpty(message = "Nome não pode ser vazio") String nome,
			@NotEmpty(message = "Clima não pode ser vazio") String clima,
			@NotEmpty(message = "Terreno não pode ser vazio") String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
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
	
	
}

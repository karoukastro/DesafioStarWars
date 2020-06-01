package com.b2w.api.responses;

import java.util.List;

/**
 * 
 * @author Carolina Castro
 * 
 * Classe para envelopar erros recebidos no retorno da API
 * 
 */
public class ErrorResponse {

	private List<String> erros;
	
	public ErrorResponse(List<String> erros) {
		this.erros = erros;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
	
}

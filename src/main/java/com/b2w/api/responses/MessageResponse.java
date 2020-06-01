package com.b2w.api.responses;

/**
 * 
 * @author Carolina Castro
 *
 * Classe para envelopar mensagens recebidas no retorno da API
 * 
 */
public class MessageResponse {

	private String message;
	
	public MessageResponse() {
		this.message = null;
	}

	public MessageResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}

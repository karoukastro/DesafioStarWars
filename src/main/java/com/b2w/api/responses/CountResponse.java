package com.b2w.api.responses;

/**
 * 
 * @author Carolina Castro
 *
 * @param <T> Tipo de resposta
 * 
 * Classe para envelopar a resposta recebida no retorno da API
 * 
 */
public class CountResponse<T> {

	private int count;
	private T results;
	
	public CountResponse(int count, T results) {
		this.results = results;
		this.count = count;
	}

	public CountResponse(T results) {
		this.results = results;
	}
	
	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


}

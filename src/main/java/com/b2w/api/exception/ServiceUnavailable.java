package com.b2w.api.exception;

public class ServiceUnavailable extends RuntimeException  {

	private static final long serialVersionUID = 1;
	
	public ServiceUnavailable(String msg) {
		super(msg);
	}
}

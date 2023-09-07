package com.project.ProgettoSad.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
*
* Eccezione lanciata nel caso in cui l'utente cerca di accedere ad una risorsa che non esiste nella repository
*
*/

@ResponseStatus
public class ExceptionResourceNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExceptionResourceNotFound(String message) {
		super(message);
	}
	
	public ExceptionResourceNotFound(String message, Throwable throwable) {
		super(message,throwable);
	}
}

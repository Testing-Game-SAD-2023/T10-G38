package com.project.ProgettoSad.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
*
* Eccezione lanciata nel caso in cui l'utente non inserisce parametri obbligatori
*
*/

@ResponseStatus
public class ExceptionMandatoryFields extends Exception {
	private static final long serialVersionUID = 23L;
	
	public ExceptionMandatoryFields(String message) {
		super(message);
	}
	
	public ExceptionMandatoryFields(String message, Throwable throwable) {
		super(message,throwable);
	}
}

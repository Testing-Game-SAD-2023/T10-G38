package com.project.ProgettoSad.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
*
* Eccezione lanciata nel caso in cui l'utente inserisce parametri non concessi
*
*/

@ResponseStatus
public class ExceptionIllegalParameters extends Exception {
		private static final long serialVersionUID = 12L;
		
		public ExceptionIllegalParameters(String message) {
			super(message);
		}
		
		public ExceptionIllegalParameters(String message, Throwable throwable) {
			super(message,throwable);
		}
}

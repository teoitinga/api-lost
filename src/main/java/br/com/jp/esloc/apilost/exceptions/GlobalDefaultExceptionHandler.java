package br.com.jp.esloc.apilost.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(value = PersonaNotFound.class)
	public String handlerPersonaNotFoundException(String message) {
		return message;
	}
}

package br.com.jp.esloc.apilost.exceptions;

import java.io.Serializable;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	@org.springframework.web.bind.annotation.ExceptionHandler({
		EmptyResultDataAccessException.class
	})
	public ResponseEntity errorNotFound(Exception exc){
		return ResponseEntity.notFound().build();
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler({
		IllegalArgumentException.class
	})
	public ResponseEntity errorIllegalArgumentException(Exception exc){
		return ResponseEntity.notFound().build();
	}
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(new ExceptionError("Operação não permitida"), HttpStatus.METHOD_NOT_ALLOWED);
	}
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(new ExceptionError("Recurso não encontrado"), HttpStatus.NOT_FOUND);

	}
	//AuthenticationException
	@org.springframework.web.bind.annotation.ExceptionHandler({
		AuthenticationException.class
	})
	protected ResponseEntity authenticationException() {
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Error("User not found."));
		
	}
	@org.springframework.web.bind.annotation.ExceptionHandler({
		AccessDeniedException.class
	})
	protected ResponseEntity accessDeniedException() {
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso negado."));
		
	}
}
class ExceptionError implements Serializable{
	private String error;

	public ExceptionError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}

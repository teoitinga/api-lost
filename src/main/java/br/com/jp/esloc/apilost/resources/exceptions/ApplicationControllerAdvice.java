package br.com.jp.esloc.apilost.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.jp.esloc.apilost.exceptions.CompraNotFound;
import br.com.jp.esloc.apilost.exceptions.RegraNegocioException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
		return new ApiErrors(ex.getMessage());
	}
	@ExceptionHandler(CompraNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handleCompraNotFoundException(CompraNotFound ex) {
		return new ApiErrors(ex.getMessage());
	}
	

}

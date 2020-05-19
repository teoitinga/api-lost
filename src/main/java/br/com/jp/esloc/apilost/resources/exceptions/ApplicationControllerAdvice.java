package br.com.jp.esloc.apilost.resources.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.jp.esloc.apilost.exceptions.CompraNotFoundException;
import br.com.jp.esloc.apilost.exceptions.ItemNotFoundException;
import br.com.jp.esloc.apilost.exceptions.PasswordInValidException;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
import br.com.jp.esloc.apilost.exceptions.RegraNegocioException;
import br.com.jp.esloc.apilost.exceptions.UserNotAutenticatedException;

@RestControllerAdvice
public class ApplicationControllerAdvice {//extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
		return new ApiErrors(ex.getMessage());
	}

	@ExceptionHandler(PersonaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlePersonaNotFoundException(PersonaNotFoundException ex) {
		return new ApiErrors(ex.getMessage());
	}

	@ExceptionHandler(CompraNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handleCompraNotFoundException(CompraNotFoundException ex) {
		return new ApiErrors(ex.getMessage());
	}

	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlerItemNotFoundException(ItemNotFoundException ex) {
		return new ApiErrors(ex.getMessage());
	}

	@ExceptionHandler(PasswordInValidException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlerPasswordInValidException(PasswordInValidException ex) {
		return new ApiErrors(ex.getMessage());
	}

	@ExceptionHandler(UserNotAutenticatedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiErrors handlerPasswordInValidException(UserNotAutenticatedException ex) {
		return new ApiErrors(ex.getMessage());
	}

	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handlerMethodInValidValidException(org.springframework.web.bind.MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getAllErrors()
				.stream()
				.map(error-> error.getDefaultMessage())
				.collect(Collectors.toList());
		return new ApiErrors(errors);
	}
}

package br.com.jp.esloc.apilost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class CompraNotFound  extends RuntimeException{

	private static final long serialVersionUID = -7564145905013804059L;

	public CompraNotFound(String message) {
		super(message);
	}
}

package br.com.jp.esloc.apilost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class ItemNotFound extends RuntimeException{

	public ItemNotFound(String message) {
		super(message);
	}
}
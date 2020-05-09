package br.com.jp.esloc.apilost.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class NoItensOnPurchase  extends RuntimeException{

	private static final long serialVersionUID = -7564145905013804059L;

	public NoItensOnPurchase(String message) {
		super(message);
	}
}

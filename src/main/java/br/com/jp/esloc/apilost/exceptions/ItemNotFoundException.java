package br.com.jp.esloc.apilost.exceptions;

public class ItemNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -1554600458466989979L;

	public ItemNotFoundException(String messageError) {
		super(messageError);
	}

}

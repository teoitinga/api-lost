package br.com.jp.esloc.apilost.exceptions;

public class ItemNotFound extends RuntimeException{

	private static final long serialVersionUID = -1554600458466989979L;

	public ItemNotFound(String messageError) {
		super(messageError);
	}

}

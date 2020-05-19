package br.com.jp.esloc.apilost.exceptions;

public class UserNotAutenticatedException extends RuntimeException {

	private static final long serialVersionUID = 678336949202310587L;

	public UserNotAutenticatedException(String messageError) {
		super(messageError);
	}
}

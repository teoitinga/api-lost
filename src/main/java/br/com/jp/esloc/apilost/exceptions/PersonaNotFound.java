package br.com.jp.esloc.apilost.exceptions;

public class PersonaNotFound extends RuntimeException {

	private static final long serialVersionUID = 5039536147849422457L;
	
	public PersonaNotFound(String messageError) {
		super(messageError);
	}

}

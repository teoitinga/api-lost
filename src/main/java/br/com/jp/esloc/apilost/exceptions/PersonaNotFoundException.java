package br.com.jp.esloc.apilost.exceptions;

public class PersonaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5039536147849422457L;
	
	public PersonaNotFoundException(String messageError) {
		super(messageError);
	}

}

package br.com.jp.esloc.apilost.exceptions;

public class PasswordInValidException extends RuntimeException {

	private static final long serialVersionUID = 9104595693962490152L;
	
	public PasswordInValidException(){
		super("Senha está incorreta.");
	}
}

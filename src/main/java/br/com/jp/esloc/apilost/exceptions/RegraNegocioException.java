package br.com.jp.esloc.apilost.exceptions;

public class RegraNegocioException extends RuntimeException{

	private static final long serialVersionUID = 5637534797056987319L;
	
	public RegraNegocioException(String messageError) {
		super(messageError);
	}
	
}

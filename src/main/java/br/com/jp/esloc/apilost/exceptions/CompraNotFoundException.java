package br.com.jp.esloc.apilost.exceptions;

public class CompraNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3378988467640914528L;
	
	public CompraNotFoundException() {
		super("Compra não encotrada nos registros.");
	}

	public CompraNotFoundException(String messageError) {
		super(messageError);
	}

}

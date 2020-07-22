package br.com.jp.esloc.apilost.exceptions;

public class RoleNotFoundException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7704984600347025530L;

	public RoleNotFoundException() {
		super("Role not found.");
	}

}

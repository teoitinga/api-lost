package br.com.jp.esloc.apilost.models;

import org.springframework.security.core.GrantedAuthority;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public enum Categoria implements GrantedAuthority {
	ADMIN("ADMIN"), 
	CLIENT("CLIENT"),
	USER("USER");

	private String categoria;

    Categoria(String cat) {
        this.categoria = cat;
    }
    public String getCategoria() {
    	return this.categoria;
    }
	@Override
	public String getAuthority() {
		return this.getCategoria();
	}

}

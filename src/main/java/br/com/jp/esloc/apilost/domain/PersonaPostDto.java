package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonaPostDto implements Serializable {

	private static final long serialVersionUID = 9187125463077849138L;

	private Integer id;
	private String nome;
	private String rg;
	private String apelido;
	private String endereco;
	private String fone;
	private Integer vendedor;
	private Integer prazo;
	private Integer state;
	private String senha;
	private String categoria;

}

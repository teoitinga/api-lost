package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PersonaPostDto implements Serializable{

	private static final long serialVersionUID = 9187125463077849138L;

    @Getter @Setter private Integer id;
    @Getter @Setter private String nome;
    @Getter @Setter private String rg;
    @Getter @Setter private String apelido;
    @Getter @Setter private String endereco;
    @Getter @Setter private String fone;
    @Getter @Setter private Integer vendedor;
    @Getter @Setter private Integer prazo;
    @Getter @Setter private Integer state;
    @Getter @Setter private String senha;
    @Getter @Setter private String categoria;

}

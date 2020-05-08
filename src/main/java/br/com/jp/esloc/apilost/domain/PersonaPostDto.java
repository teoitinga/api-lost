package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PersonaPostDto implements Serializable{

	private static final long serialVersionUID = 9187125463077849138L;

	@Basic(optional = false)
    @Column(name = "id", nullable = false)
    @Getter @Setter private Integer id;
    @Column(name = "nome", length = 255)
    @Getter @Setter private String nome;
    @Column(name = "rg", length = 50)
    @Getter @Setter private String rg;
    @Column(name = "apelido", length = 255)
    @Getter @Setter private String apelido;
    @Column(name = "endereco", length = 255)
    @Getter @Setter private String endereco;
    @Column(name = "fone", length = 255)
    @Getter @Setter private String fone;
    @Column(name = "usuario")
    @Getter @Setter private Integer usuario;
    @Column(name = "prazo")
    @Getter @Setter private Integer prazo;
    @Column(name = "state")
    @Getter @Setter private Integer state;
    @Column(name = "senha")
    @Getter @Setter private String senha;
    @Column(name = "categoria")
    @Getter @Setter private String categoria;

}

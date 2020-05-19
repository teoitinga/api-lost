package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PersonaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	private String nome;
	private String rg;
	private String apelido;
	private String endereco;
	private String fone;
	private LocalDateTime dataCadastro;
	private Integer usuario;
	private Integer prazo;
	private Integer state;
	private LocalDateTime ultAtualizacao;
	private String senha;
	private String categoria;
	private Double debito;

}

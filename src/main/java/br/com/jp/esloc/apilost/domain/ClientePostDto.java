package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.jp.esloc.apilost.models.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClientePostDto implements Serializable {

	private static final long serialVersionUID = 6489501381487196479L;

	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	private String nome;
	private String rg;
	private String apelido;
	private String endereco;
	private String fone;
	@NotNull(message = "{vendedor.not.blank}")
	private Integer vendedor;
	private Integer prazo;
	private Integer state;
	private String categoria;

	public ClientePostDto() {
		if(this.prazo==null ||this.prazo == 0) {
			this.setPrazo(30);
		}
		
		this.setCategoria("c");

		this.setState(1);
		
	}
	
    public static ClientePostDto create(Persona persona) {
    	
    	return ClientePostDto.builder()
		.id(persona.getId())
		.nome(persona.getNome())
		.rg(persona.getRg())
		.apelido(persona.getApelido())
		.endereco(persona.getEndereco())
		.fone(persona.getFone())
		.vendedor(persona.getUsuario())
		.prazo(persona.getPrazo())
		.state(persona.getState())
		.categoria(persona.getCategoria())
		.build();
    	
	}	
}

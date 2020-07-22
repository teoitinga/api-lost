package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.jp.esloc.apilost.models.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPutDto  implements Serializable {

	private static final long serialVersionUID = 6255833950172808642L;
	
	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	private String nome;
	private String rg;
	private String apelido;
	private String endereco;
	@NotBlank(message = "Uma senha de 06 caracteres deve ser informada.")
	@Size(min = 6, max = 6, message = "A senha deve ter 06 caracteres.")
	private String senha;
	private String fone;
	private String role;
	
    public static UserPutDto create(Persona persona) {
    	String authority = persona.getRoles().get(0).getAuthority();
    	
    	return UserPutDto.builder()
		.id(persona.getId())
		.nome(persona.getNome())
		.rg(persona.getRg())
		.apelido(persona.getApelido())
		.endereco(persona.getEndereco())
		.fone(persona.getFone())
		.senha(persona.getSenha())
		.role(authority)
		.build();
	}	
}

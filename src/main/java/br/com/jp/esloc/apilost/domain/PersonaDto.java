package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.jp.esloc.apilost.models.Persona;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PersonaDto implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Getter @Setter private Integer id;
    @Getter @Setter private String nome;
    @Getter @Setter private String rg;
    @Getter @Setter private String apelido;
    @Getter @Setter private String endereco;
    @Getter @Setter private String fone;
    @Getter @Setter private LocalDateTime dataCadastro;
    @Getter @Setter private Integer usuario;
    @Getter @Setter private Integer prazo;
    @Getter @Setter private Integer state;
    @Getter @Setter private LocalDateTime ultAtualizacao;
    @Getter @Setter private String senha;
    @Getter @Setter private String categoria;
    @Getter @Setter private Double debito;

    public static PersonaDto create(Persona persona) {
    	ModelMapper model = new ModelMapper();
    	return model.map(persona, PersonaDto.class);
    }
}

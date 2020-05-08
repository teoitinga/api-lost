package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.modelmapper.ModelMapper;

import br.com.jp.esloc.apilost.models.Persona;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PersonaDto implements Serializable{
	
    private static final long serialVersionUID = 1L;

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
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter private LocalDateTime dataCadastro;
    @Column(name = "usuario")
    @Getter @Setter private Integer usuario;
    @Column(name = "prazo")
    @Getter @Setter private Integer prazo;
    @Column(name = "state")
    @Getter @Setter private Integer state;
    @Column(name = "ultAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter private LocalDateTime ultAtualizacao;
    @Column(name = "senha")
    @Getter @Setter private String senha;
    @Column(name = "categoria")
    @Getter @Setter private String categoria;
    @Column(name = "debito", precision = 22)
    @Getter @Setter private Double debito;

    public static PersonaDto create(Persona persona) {
    	ModelMapper model = new ModelMapper();
    	return model.map(persona, PersonaDto.class);
    }
}

package br.com.jp.esloc.apilost.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(of={"id"})
@Data
@Slf4j
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "nome", length = 255)
    private String nome;
    @Column(name = "rg", length = 50)
    private String rg;
    @Column(name = "apelido", length = 255)
    private String apelido;
    @Column(name = "endereco", length = 255)
    private String endereco;
    @Column(name = "fone", length = 255)
    private String fone;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Column(name = "usuario")
    private Integer usuario;
    @Column(name = "prazo")
    private Integer prazo;
    @Column(name = "state")
    private Integer state;
    @Column(name = "ultAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultAtualizacao;
    @Column(name = "senha")
    private String senha;
    @Column(name = "categoria")
    private String categoria;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "debito", precision = 22)
    private Double debito;

}

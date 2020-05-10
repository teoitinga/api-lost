package br.com.jp.esloc.apilost.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "perfil")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(of={"id"})
@Data
public class Profile implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private String id;
	@Column(name = "DSC")
	private String dsc;
	@Column(name = "DESENV")
	private String desenv;
	@Column(name = "CONTATODESV")
	private String contatodesv;
	@Column(name = "VERSAO")
	private String versao;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "CODINSTALL")
	private String codinstall;
	@Column(name = "UPDATEKEY")
	private String updatekey;
	@Column(name = "ESTABELECIMENTO")
	private String estabelecimento;
	@Column(name = "ENDERECOEST")
	private String enderecoest;
	@Column(name = "FONEEST")
	private String foneest;
	@Column(name = "RESPONSAVELEST")
	private String responsavelest;
	@Column(name = "MSG1EST")
	private String msg1est;
	@Column(name = "MSG2EST")
	private String msg2est;
	@Column(name = "CIDADEEST")
	private String cidadeest;
	@Column(name = "ULTUPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultupdate;
	@Column(name = "TEMPOEXPIRA")
	private Integer tempoexpira;
	@Column(name = "ESTADO")
	private String estado;
	@Column(name = "VALORINSTALL")
	private Double valorinstall;
	@Column(name = "VALORMENSAL")
	private Double valormensal;
	@Column(name = "DRIVERBCK")
	private String driverbck;

}

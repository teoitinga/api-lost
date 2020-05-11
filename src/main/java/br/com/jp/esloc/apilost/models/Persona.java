package br.com.jp.esloc.apilost.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@ToString(exclude = "id")
@EqualsAndHashCode(of = { "id" })
@Data
public class Persona implements UserDetails, Serializable {

	private static final long serialVersionUID = -4586092888625020736L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	@Getter
	@Setter
	private Integer id;
	@Column(name = "nome", length = 255)
	@Getter
	@Setter
	private String nome;
	@Column(name = "rg", length = 50)
	@Getter
	@Setter
	private String rg;
	@Column(name = "apelido", length = 255)
	@Getter
	@Setter
	private String apelido;
	@Column(name = "endereco", length = 255)
	@Getter
	@Setter
	private String endereco;
	@Column(name = "fone", length = 255)
	@Getter
	@Setter
	private String fone;
	@Column(name = "data_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date dataCadastro;
	@Column(name = "usuario")
	@Getter
	@Setter
	private Integer usuario;
	@Column(name = "prazo")
	@Getter
	@Setter
	private Integer prazo;
	@Column(name = "state")
	@Getter
	@Setter
	private Integer state;
	@Column(name = "ultAtualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	private Date ultAtualizacao;
	@Column(name = "senha")
	@Getter
	@Setter
	private String senha;
	@Column(name = "categoria")
	@Enumerated(EnumType.STRING)
	@Getter
	@Setter
	private Categoria categoria;
	@Column(name = "debito", precision = 22)
	@Getter
	@Setter
	private Double debito;

	public Persona(String nome, String rg, String apelido, String endereco, String fone, Date dataCadastro,
			Integer usuario, Integer prazo, Integer state, Date ultAtualizacao, String senha, String categoria,
			Double debito) {
		this.nome = nome;
		this.rg = rg;
		this.apelido = apelido;
		this.endereco = endereco;
		this.fone = fone;
		this.dataCadastro = dataCadastro;
		this.usuario = usuario;
		this.prazo = prazo;
		this.state = state;
		this.ultAtualizacao = ultAtualizacao;
		this.senha = senha;
		this.categoria = Categoria.valueOf(categoria);
		this.debito = debito;
	}

	public Persona(String nome) {
		this.nome = nome;
	}
	public static Categoria valueOf(String cat) {
		for(Categoria status : Categoria.values()) {
			if(status.getCategoria() == cat)
				return status;
		}
		throw new IllegalArgumentException("Categoria " + cat + " is not valid!");
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(this.categoria);
	}

	@Override
	public String getPassword() {

		return this.senha;
	}

	@Override
	public String getUsername() {

		return String.valueOf(this.getId());
	}

	@Override
	public boolean isAccountNonExpired() {
		return !false;
	}

	@Override
	public boolean isAccountNonLocked() {

		return !false;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return !false;
	}

	@Override
	public boolean isEnabled() {

		return !false;
	}

	@PrePersist
	private void setCadastro() {
		this.dataCadastro = new Date();
	}

	@PreUpdate
	private void setUpdate() {
		this.ultAtualizacao = new Date();
	}
}

package br.com.jp.esloc.apilost.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode(of = { "id" })
@Data
@Builder
@AllArgsConstructor
public class Persona implements UserDetails, Serializable {

	private static final long serialVersionUID = -4586092888625020736L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nome", length = 255)
	@NotEmpty(message = "É necessário informar um nome para que possa ser registrado")
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
	private LocalDate dataCadastro;
	
	@Column(name = "usuario")
	private Integer usuario;
	
	@Column(name = "prazo")
	private Integer prazo;
	
	@Column(name = "state")
	private Integer state;
	
	@Column(name = "ultAtualizacao")
	private LocalDateTime ultAtualizacao;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "categoria")
	private String categoria;

	@Enumerated(EnumType.STRING)
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "user_roles",
		joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
	private List<Role> roles;

	@Column(name = "debito", precision = 22)
	private BigDecimal debito;

	public Persona(String nome, String rg, String apelido, String endereco, String fone, LocalDate dataCadastro,
			Integer usuario, Integer prazo, Integer state, LocalDateTime ultAtualizacao, String senha, String categoria,
			BigDecimal debito) {
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
		this.categoria = categoria;
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
		return this.roles;
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
		this.dataCadastro = LocalDate.now();
	}

	@PreUpdate
	private void setUpdate() {
		this.ultAtualizacao = LocalDateTime.now();
	}

}

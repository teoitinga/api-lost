package br.com.jp.esloc.apilost.models;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode(of = { "id" })
@Data
public class Role implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 3262228163476170048L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "role", length = 255)
	private String permissao;
	@Override
	public String getAuthority() {
		return this.permissao;
	}
}

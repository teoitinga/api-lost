package br.com.jp.esloc.apilost.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(of={"id"})
@Data
public class Detalhecompra implements Serializable {

	private static final long serialVersionUID = 1824632535341214158L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkcompra")
    private Compra compra;
    
	@Column(name = "dsc", length = 255)
    private String dsc;
    
	@Column(name = "vlunit", precision = 22)
    private BigDecimal vlunit;
    
	@Column(name = "qtd", precision = 22)
    private BigDecimal qtd;
    
	@Column(name = "vltotal", precision = 22)
    private BigDecimal vltotal;
    
	@Column(name = "desconto", precision = 22)
    private BigDecimal desconto;
}

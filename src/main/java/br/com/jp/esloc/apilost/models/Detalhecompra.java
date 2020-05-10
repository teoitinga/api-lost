package br.com.jp.esloc.apilost.models;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "fkcompra")
    private Integer fKcompra;
    @Column(name = "dsc", length = 255)
    private String dsc;
    @Column(name = "vlunit", precision = 22)
    private Double vlunit;
    @Column(name = "qtd", precision = 22)
    private Double qtd;
    @Column(name = "vltotal", precision = 22)
    private Double vltotal;
    @Column(name = "desconto", precision = 22)
    private Double desconto;
}

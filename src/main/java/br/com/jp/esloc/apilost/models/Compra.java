package br.com.jp.esloc.apilost.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "data_compra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCompra;
    @Column(name = "entregue_a", length = 255)
    private String entregueA;
    @Column(name = "entregue_por")
    private Integer entreguePor;
    //@Column(name = "fk_cliente")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cliente")
    private Persona fkCliente;
    @Column(name = "acertado_em")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acertadoEm;
    @Column(name = "valor_compra")
    private Double valorCompra;
    @Column(name = "debAtual")
    private Double debAtual;
    
}

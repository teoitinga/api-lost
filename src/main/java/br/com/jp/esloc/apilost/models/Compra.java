package br.com.jp.esloc.apilost.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(of={"id"})
@Data
public class Compra implements Serializable {

	private static final long serialVersionUID = 4570415835162885090L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "data_compra")
    private LocalDateTime dataCompra;
    
    @Column(name = "entregue_a", length = 255)
    private String entregueA;
    
    //@Column(name = "entregue_por")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entregue_por")
    private Persona entreguePor;
    
    //@Column(name = "fk_cliente")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cliente")
    private Persona fkCliente;

    @Column(name = "acertado_em")
    private Date acertadoEm;

    @Column(name = "valor_compra")
    private BigDecimal valorCompra;
    
    @Column(name = "debAtual")
    private BigDecimal debAtual;
    
    @OneToMany(mappedBy = "compra")
    private List<Detalhecompra> itens;
}

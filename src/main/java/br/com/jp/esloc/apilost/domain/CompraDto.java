package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.modelmapper.ModelMapper;

import br.com.jp.esloc.apilost.models.Compra;
import br.com.jp.esloc.apilost.models.Persona;
import lombok.Data;

@Data
public class CompraDto implements Serializable{

	private static final long serialVersionUID = 401251212136707719L;

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
    @Column(name = "fk_cliente")
    private Integer fkCliente;
    @Column(name = "acertado_em")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acertadoEm;
    @Column(name = "valor_compra", precision = 22)
    private Double valorCompra;
    @Column(name = "debAtual", precision = 22)
    private Double debAtual;
    
    public static CompraDto create(Compra compra) {
    	ModelMapper model = new ModelMapper();
    	return model.map(compra, CompraDto.class);
    }
}

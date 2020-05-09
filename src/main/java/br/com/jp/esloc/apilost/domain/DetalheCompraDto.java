package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.modelmapper.ModelMapper;

import br.com.jp.esloc.apilost.models.Detalhecompra;
import lombok.Data;

@Data
public class DetalheCompraDto implements Serializable{

	private static final long serialVersionUID = -4435584120185047677L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "FK_compra")
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
    
    public static DetalheCompraDto create(Detalhecompra item) {
    	ModelMapper model = new ModelMapper();
    	return model.map(item, DetalheCompraDto.class);
    }
}

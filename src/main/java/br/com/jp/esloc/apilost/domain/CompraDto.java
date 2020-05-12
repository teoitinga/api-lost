package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.modelmapper.ModelMapper;

import br.com.jp.esloc.apilost.models.Compra;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(of={"id"})
@Data
public class CompraDto implements Serializable{

	private static final long serialVersionUID = 401251212136707719L;

    private Integer id;
    private Date dataCompra;
    private String entregueA;
    private Integer entreguePor;
    private String UserNome;
    private Integer fkCliente;
    private String clienteNome;
    private Date acertadoEm;
    private Double valorCompra;
    private Double debAtual;
    private Set<ItensDto> itens;

}

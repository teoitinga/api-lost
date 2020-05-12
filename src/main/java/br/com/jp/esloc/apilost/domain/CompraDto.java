package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(of={"id"})
@Data
public class CompraDto implements Serializable{

	private static final long serialVersionUID = 401251212136707719L;

    private Integer id;
    private LocalDate dataCompra;
    private String entregueA;
    private Integer entreguePor;
    private String UserNome;
    private Integer fkCliente;
    private String clienteNome;
    private LocalDate acertadoEm;
    private BigDecimal valorCompra;
    private BigDecimal debAtual;
    private List<ItensDto> itens;

}

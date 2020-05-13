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
@ToString(exclude = "id")
@EqualsAndHashCode(of = { "id" })
@Data
public class CompraResponseDto implements Serializable {

	private static final long serialVersionUID = -4832223015919582035L;
	private Integer id;
	private LocalDate dataCompra;
	private String entregueA;
	private String entreguePorNome;
	private String fkClienteNome;
	private BigDecimal valorCompra;
	private List<ItensDto> itens;

}

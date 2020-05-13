package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode(of = { "id" })
@Data
@Builder
public class CompraResponseDto implements Serializable {

	private static final long serialVersionUID = -4832223015919582035L;
	private Integer codigo;
	private LocalDateTime dataCompra;
	private String recebedor;
	private String vendedor;
	private String clienteNome;
	private BigDecimal valorCompra;
	private List<ItensDto> itens;

}

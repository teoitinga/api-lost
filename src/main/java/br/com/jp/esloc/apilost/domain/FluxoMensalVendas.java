package br.com.jp.esloc.apilost.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class FluxoMensalVendas {
	private Integer mes;
	private Integer ano;
    @Digits(integer=3, fraction=2)
	private BigDecimal valor;
	@Digits(integer=3, fraction=2)
	private Integer quantidade;
}

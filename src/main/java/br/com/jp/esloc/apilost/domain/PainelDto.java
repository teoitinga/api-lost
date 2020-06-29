package br.com.jp.esloc.apilost.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class PainelDto {
	
	private BigDecimal totalQuitado;
	private BigDecimal totalVendido;
	private BigDecimal totalDebitos;
	private BigDecimal totalQuitadoPorUsuario;
	private BigDecimal totalVendidoPorUsuario;
	private BigDecimal totalDebitosPorUsuario;
	private LocalDate dataAtual;
	
}

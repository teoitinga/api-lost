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
public class PerfilDto {
	private String estabelecimento;
	private String endereco;
	private String cidade;
	private String telefone;
	private String proprietario;
	private LocalDate dataExpiracao;
	private BigDecimal valorMensal;
	private String msgHead;
	private String msgBody;
	private String desenvolvedor;
	private String contatoDesenvolvedor;
	private String driverBackup;
	private String emailSistema;
}

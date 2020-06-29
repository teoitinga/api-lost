package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ParcialPayDto  implements Serializable {
	private static final long serialVersionUID = 8264898173088827801L;
	private Integer id;
	@NotBlank(message = "É necessário informar o nome de quem está fazendo o pagamento.")
	private String pagador;
	@NotBlank(message = "É necessário informar o valor do pagamento.")
	private BigDecimal valor;
	private LocalDateTime data;
	private String cliente;
	private String vendedor;
	@NotBlank(message = "É necessário informar uma descrição para se referir a este pagamento.")
	private String descricao;

}

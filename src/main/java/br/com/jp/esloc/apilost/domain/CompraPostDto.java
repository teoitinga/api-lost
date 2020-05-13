package br.com.jp.esloc.apilost.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(exclude="id")
@EqualsAndHashCode(of={"id"})
@Data
public class CompraPostDto implements Serializable{

	private static final long serialVersionUID = 401251212136707719L;

    private Integer id;
    private LocalDateTime dataCompra;
    @NotNull(message = "É necessário informar o nome de quem recebeu a compra")
    private String recebebida;
    @NotNull(message = "É necessário informar o codigo do vendedor")
    private Integer vendedor;
    @NotNull(message = "É necessário informar o codigo do cliente")
    private Integer cliente;
    private List<ItensDto> itens;

}

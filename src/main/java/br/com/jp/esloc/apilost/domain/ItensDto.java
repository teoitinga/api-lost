package br.com.jp.esloc.apilost.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of={"id"})
@Data
public class ItensDto {

    private Integer id;
    private String dsc;
    private BigDecimal vlunit;
    private BigDecimal qtd;
    private BigDecimal vltotal;
    private BigDecimal desconto;
    
}

package br.com.jp.esloc.apilost.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of={"id"})
@Data
@Builder
public class ItensDto {

    private Integer id;
    private String description;
    private BigDecimal unitvalue;
    private BigDecimal qtd;
    private BigDecimal desconto;
    
}

package br.com.jp.esloc.apilost.domain;

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
    private Double vlunit;
    private Double qtd;
    private Double vltotal;
    private Double desconto;
    
}

package br.com.jp.esloc.apilost.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString(exclude = "id")
    @EqualsAndHashCode(of = { "id" })
    @Data
    @Builder
    public class ComprasListDto implements Serializable {

        private Integer codigo;
        private LocalDateTime dataCompra;
        private LocalDateTime dataPagamento;
        private String recebedor;
        private String vendedor;
        private String clienteNome;
        private BigDecimal valorCompra;
        private Integer ItemId;
        private String itemDescription;
        private BigDecimal ItemUnitvalue;
        private BigDecimal ItemQtd;
        private BigDecimal ItemDesconto;

    }

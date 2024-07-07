package com.exalt.it.bank.system.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private Long id;
    private String operationLibelle;
    private LocalDateTime localDateTime;
    private BigDecimal montant;
    private Compte compte;

}

package com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationReponse {
    private String operationLibelle;
    private LocalDateTime localDateTime;
    private BigDecimal montant;
}

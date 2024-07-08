package com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivretRequest {
    public String name;
    public BigDecimal solde=BigDecimal.ZERO;
    public Boolean isActive = Boolean.TRUE;
    public BigDecimal plafondDepot=BigDecimal.ZERO;
}

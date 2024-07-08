package com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response;

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
public class CompteReponse {
    public Long id;
    public String name;
    public String numeroCompte;
    public Boolean isActive;
    public String typeCompte;
    public String solde;
    public BigDecimal plafondDepot;
    
}

package com.exalt.it.bank.system.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Livret extends Compte {
    private BigDecimal plafondDepot=BigDecimal.ZERO;

    @Override
    public boolean depotArgent(BigDecimal argent) {

        if (plafondDepot.compareTo(argent.add(solde)) >= 0) {
            solde = solde.add(argent);
            return true;
        }
        return false;
    }


}

package com.exalt.it.bank.system.domain.model;

import com.exalt.it.bank.system.domain.enums.TypeCompte;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Compte {
    protected Long id;
    protected String name;
    protected String numeroCompte;
    protected BigDecimal solde = BigDecimal.ZERO;
    protected Boolean isActive = Boolean.TRUE;
    protected TypeCompte typeCompte;
    protected List<Operation> operations = new ArrayList<>();
    private BigDecimal plafondDecouvert = BigDecimal.ZERO;

    public Compte() {
    }

    public boolean depotArgent(BigDecimal argent) {
        solde = solde.add(argent);
        return true;
    }

    public boolean retraitArgent(BigDecimal argent) {
        // le solde deviendera négatif tant qu'il ne dépasse pas le plafond

        if (plafondDecouvert != null && solde.add(plafondDecouvert).compareTo(argent) >= 0) {
            solde = solde.subtract(argent);
            return true;
        }

        return false;
    }

}

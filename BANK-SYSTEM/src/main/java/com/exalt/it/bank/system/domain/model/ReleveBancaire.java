package com.exalt.it.bank.system.domain.model;

import com.exalt.it.bank.system.domain.enums.TypeCompte;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
public class ReleveBancaire {
    private List<Operation> operations;
    private TypeCompte typeCompte;
    private BigDecimal sold;
}

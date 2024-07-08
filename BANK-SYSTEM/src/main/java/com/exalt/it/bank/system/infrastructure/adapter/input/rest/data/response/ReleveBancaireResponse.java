package com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReleveBancaireResponse {
    private List<OperationReponse> operations;
    private String typeCompte;
    private String sold;
}

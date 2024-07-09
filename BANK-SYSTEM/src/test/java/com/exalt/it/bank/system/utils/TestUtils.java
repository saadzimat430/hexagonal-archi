package com.exalt.it.bank.system.utils;

import com.exalt.it.bank.system.domain.enums.TypeCompte;
import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.domain.model.Livret;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.request.CompteRequest;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.CompteEntity;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.LivretEntity;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.OperationEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TestUtils {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    String GET_COMPTES_URL = "/comptes";
    String GET_COMPTE_URL = "/comptes/";
    String POST_COMPTE_URL = "/comptes";
    String POST_LIVRET_URL = "/comptes/livret";
    String PUT_DECOUVERT_URL = "/comptes/{compteId}/decouvert";
    String DELETE_COMPTE = "/comptes/{compteId}";
    String RELEVE_BANCAIRE_URL = "/comptes/{compteId}/releve";
    String RETRAIT_ARGENT_URL = "/operations/retrait/{compteId}";
    String DEPOT_ARGENT_URL = "/operations/depot/{compteId}";

    static List<CompteEntity> getMockComptes() {
        return List.of(
                new CompteEntity(),
                new CompteEntity(),
                new CompteEntity()
        );
    }

    static Optional<CompteEntity> getMockCompte() {
        CompteEntity compteEntity = new CompteEntity();
        compteEntity.setId(1L);
        compteEntity.setPlafondDecouvert(BigDecimal.valueOf(200));
        compteEntity.setTypeCompte(TypeCompte.COURANT);
        compteEntity.setOperations(new ArrayList<>());
        return Optional.of(compteEntity);
    }

    static Optional<CompteEntity> getMockLivretCompte() {
        LivretEntity livret = new LivretEntity();
        livret.setId(1L);
        livret.setTypeCompte(TypeCompte.LIVRET);
        livret.setPlafondDepot(BigDecimal.valueOf(500));

        return Optional.of(livret);
    }

    static CompteRequest getMockCompteRequest() {
        CompteRequest compteRequest = new CompteRequest();
        compteRequest.setName("compte");
        compteRequest.setSolde(BigDecimal.ONE);
        return compteRequest;
    }

    static String convertJsonToString(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

    static OperationEntity getMockOperation() {
        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setId(1L);
        return operationEntity;
    }

    static Compte getMockCompteToAdd() {
        Compte compte = new Compte();
        compte.setName("EXALT");
        compte.setTypeCompte(TypeCompte.COURANT);
        compte.setSolde(BigDecimal.valueOf(1000));
        return compte;
    }

    static Livret getMockLivretToAdd() {
        Livret livret = new Livret();
        livret.setName("LIVRET");
        livret.setTypeCompte(TypeCompte.LIVRET);
        livret.setSolde(BigDecimal.valueOf(1000));
        livret.setPlafondDepot(BigDecimal.valueOf(2000));
        return livret;
    }

}

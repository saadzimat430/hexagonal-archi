package com.exalt.it.bank.system.domain.service;

import com.exalt.it.bank.system.domain.input.CompteInput;
import com.exalt.it.bank.system.application.port.out.CompteOutput;
import com.exalt.it.bank.system.domain.enums.TypeCompte;
import com.exalt.it.bank.system.domain.enums.TypeOperation;
import com.exalt.it.bank.system.domain.exceptions.CompteNoFound;
import com.exalt.it.bank.system.domain.exceptions.OperationUnothorized;
import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.domain.model.Livret;
import com.exalt.it.bank.system.domain.model.Operation;
import com.exalt.it.bank.system.domain.model.ReleveBancaire;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CompteService implements CompteInput {
    private final CompteOutput compteOutput;

    @Override
    public Compte createCompte(Compte compte) {
        compte.setNumeroCompte(UUID.randomUUID().toString());
        if (compte instanceof Livret) {
            Livret livret = (Livret) compte;
            livret.setTypeCompte(TypeCompte.LIVRET);

            return compteOutput.saveCompte(livret);
        }
        return compteOutput.saveCompte(compte);
    }

    @Override
    public Compte updateCompte(Compte compte) {
        return compteOutput.updateCompte(compte);
    }

    @Override
    public Compte getCompteById(Long id) {
        return compteOutput.getCompteById(id).orElseThrow(() -> new CompteNoFound("Compte avec id [" + id + "] n'existe pas."));
    }

    @Override
    public Compte retraitArgent(BigDecimal argent, Long idCompte) {
        Compte compte = getCompteById(idCompte);
        boolean retrait = compte.retraitArgent(argent);
        if (!retrait) {
            throw new OperationUnothorized("Operation non autorisée !");
        }
        Operation operation = Operation.builder()
                .montant(argent)
                .operationLibelle(TypeOperation.RETRAIT.name())
                .compte(compte)
                .localDateTime(LocalDateTime.now())
                .build();
        compte.getOperations().add(operation);

        return updateCompte(compte);
    }

    @Override
    public Compte depotArgent(BigDecimal argent, Long idCompte) {
        Compte compte = getCompteById(idCompte);
        boolean depot = compte.depotArgent(argent);
        if (!depot) {
            throw new OperationUnothorized("Operation non autorisée !");
        }

        Operation operation = Operation.builder().montant(argent).operationLibelle(TypeOperation.DEPOT.name()).compte(compte).localDateTime(LocalDateTime.now()).build();
        compte.getOperations().add(operation);
        return updateCompte(compte);
    }

    @Override
    public ReleveBancaire getReleveBancaire(Long idCompte) {
        Compte compte = getCompteById(idCompte);
        List<Operation> operations = compte.getOperations();
        operations.sort((o1, o2) -> o2.getLocalDateTime().compareTo(o1.getLocalDateTime()));
        return ReleveBancaire.builder().operations(compte.getOperations())
                .typeCompte(compte.getTypeCompte())
                .sold(compte.getSolde()).build();
    }

    @Override
    public Compte deleteCompte(Long idCompte) {
        Compte compte = getCompteById(idCompte);
        compte.setIsActive(Boolean.FALSE);
        return updateCompte(compte);

    }

    @Override
    public List<Compte> getAllComptes() {
        return compteOutput.getAllComptes();
    }

    @Override
    public String activerDecouvert(Long compteId, BigDecimal plafondDecouvert) {

        if (plafondDecouvert.compareTo(BigDecimal.ZERO) < 0) {
            throw new OperationUnothorized("Le plafond de découvert doit être supérieur à 0");
        }
        Compte compte = getCompteById(compteId);
        if (plafondDecouvert.compareTo(compte.getPlafondDecouvert()) > 0 && compte.getSolde().compareTo(BigDecimal.ZERO) < 0) {
            throw new OperationUnothorized("Vous devez neutralisez votre sold avant d'agrandir le decouvert, votre solde est est [" + compte.getSolde() + "]");
        }
        if (compte.getTypeCompte().equals(TypeCompte.LIVRET)) {
            throw new OperationUnothorized("Un compte Livret ne peut pas avoir un découvert.");
        }
        compte.setPlafondDecouvert(plafondDecouvert);
        updateCompte(compte);
        return "Decouvert activé avec succès : [" + plafondDecouvert + "]";
    }
}

package com.exalt.it.bank.system.domain.input;

import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.domain.model.ReleveBancaire;

import java.math.BigDecimal;
import java.util.List;

public interface CompteInput {
    Compte createCompte(Compte compte);
    Compte updateCompte(Compte compte);
    Compte getCompteById(Long id);
    Compte retraitArgent(BigDecimal argent,Long idCompte);
    Compte depotArgent(BigDecimal argent,Long idCompte);
    ReleveBancaire getReleveBancaire(Long compteId);
    Compte deleteCompte(Long compteId);
    List<Compte> getAllComptes();

    String activerDecouvert(Long compteId, BigDecimal plafondDecouvert);
}

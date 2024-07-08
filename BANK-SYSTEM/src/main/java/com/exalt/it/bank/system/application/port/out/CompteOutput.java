package com.exalt.it.bank.system.application.port.out;

import com.exalt.it.bank.system.domain.model.Compte;

import java.util.List;
import java.util.Optional;

public interface CompteOutput {
    Compte saveCompte(Compte compte);
    Optional<Compte> getCompteById(Long id);
    Compte updateCompte(Compte compte);
    List<Compte> getAllComptes();
}

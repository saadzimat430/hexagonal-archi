package com.exalt.it.bank.system.infrastructure.adapter.input.rest.controller;

import com.exalt.it.bank.system.domain.input.CompteInput;
import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.domain.model.Livret;
import com.exalt.it.bank.system.domain.model.ReleveBancaire;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.request.CompteRequest;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.request.LivretRequest;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response.CompteReponse;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response.ReleveBancaireResponse;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.mapper.CompteRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/comptes")
@RequiredArgsConstructor
public class CompteRestAdapter {

    private final CompteInput compteInput;
    private final CompteRestMapper compteRestMapper = new CompteRestMapper();

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompteReponse createCompte(@RequestBody @Validated CompteRequest compteRequest) {

        Compte compte = compteRestMapper.toCompte(compteRequest);

        compte = compteInput.createCompte(compte);

        return compteRestMapper.toCompteResponse(compte);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/livret")
    public CompteReponse createLivret(@RequestBody @Validated LivretRequest livretRequest) {

        Livret livret = compteRestMapper.toLivret(livretRequest);

        livret = (Livret) compteInput.createCompte(livret);

        return compteRestMapper.toCompteResponse(livret);
    }

    @GetMapping()
    public List<CompteReponse> getAllComptes() {

        List<Compte> comptes = compteInput.getAllComptes();

        return compteRestMapper.toListCompteReponse(comptes);
    }

    @GetMapping("/{compteId}")
    public CompteReponse getCompte(@PathVariable Long compteId) {

        Compte compte = compteInput.getCompteById(compteId);

        return compteRestMapper.toCompteResponse(compte);
    }

    @DeleteMapping("/{compteId}")
    public CompteReponse deleteCompte(@PathVariable Long compteId) {

        Compte compte = compteInput.deleteCompte(compteId);

        return compteRestMapper.toCompteResponse(compte);
    }

    @GetMapping("/{compteId}/releve")
    public ReleveBancaireResponse getReleveBancaire(@PathVariable Long compteId) {

        ReleveBancaire releveBancaire = compteInput.getReleveBancaire(compteId);
        return compteRestMapper.toReleveBancaireResponse(releveBancaire);
    }

    @PutMapping("/{compteId}/decouvert")
    public String activerDecouvert(@PathVariable Long compteId, @RequestParam(defaultValue = "0") BigDecimal plafondDecouvert) {

        return compteInput.activerDecouvert(compteId, plafondDecouvert);
    }
}

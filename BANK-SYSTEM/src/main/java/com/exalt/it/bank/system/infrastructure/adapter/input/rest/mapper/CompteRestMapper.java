package com.exalt.it.bank.system.infrastructure.adapter.input.rest.mapper;

import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.domain.model.Livret;
import com.exalt.it.bank.system.domain.model.ReleveBancaire;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.request.CompteRequest;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.request.LivretRequest;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response.CompteReponse;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response.ReleveBancaireResponse;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CompteRestMapper {
    public  final ModelMapper modelMapper = new ModelMapper();

    public  Compte toCompte(CompteRequest compteRequest) {
        return modelMapper.map(compteRequest, Compte.class);
    }

    public  CompteReponse toCompteResponse(Compte compte) {
        return modelMapper.map(compte, CompteReponse.class);
    }

    public  List<CompteReponse> toListCompteReponse(List<Compte> comptes) {
        return comptes.stream().map(compte -> modelMapper.map(compte, CompteReponse.class)).collect(Collectors.toList());
    }
    public  Livret toLivret(LivretRequest livretRequest) {
        return modelMapper.map(livretRequest, Livret.class);
    }

    public  ReleveBancaireResponse toReleveBancaireResponse(ReleveBancaire releveBancaire) {
        return modelMapper.map(releveBancaire, ReleveBancaireResponse.class);
    }
}

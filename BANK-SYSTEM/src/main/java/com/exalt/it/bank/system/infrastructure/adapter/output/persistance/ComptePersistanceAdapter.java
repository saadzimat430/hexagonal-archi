package com.exalt.it.bank.system.infrastructure.adapter.output.persistance;


import com.exalt.it.bank.system.application.port.out.CompteOutput;
import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.domain.model.Livret;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.CompteEntity;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.LivretEntity;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ComptePersistanceAdapter implements CompteOutput {
    private final CompteRepository compteRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Compte saveCompte(Compte compte) {
        CompteEntity compteEntity = modelMapper.map(compte, compte instanceof Livret ? LivretEntity.class : CompteEntity.class);

        return modelMapper.map(compteRepository.save(compteEntity), compteEntity instanceof LivretEntity ? Livret.class : Compte.class);
    }

    @Override
    public Optional<Compte> getCompteById(Long id) {
        Optional<CompteEntity> compteOptional = compteRepository.findById(id);
        if (compteOptional.isEmpty()) return Optional.empty();
        return Optional.of(modelMapper.map(compteOptional.get(), compteOptional.get() instanceof LivretEntity ? Livret.class : Compte.class));
    }


    @Override
    public Compte updateCompte(Compte compte) {
        return saveCompte(compte);
    }

    @Override
    public List<Compte> getAllComptes() {
        // TODO: 03/07/2024  pour aller plus loin, mettre une pagination pour eviter de tout extraire de la BD

        List<CompteEntity> comptes = compteRepository.findAll();
        return comptes.stream().map(compte -> modelMapper.map(compte, Compte.class)).collect(Collectors.toList());
    }


}

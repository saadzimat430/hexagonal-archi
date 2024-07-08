package com.exalt.it.bank.system.infrastructure.config;


import com.exalt.it.bank.system.domain.service.CompteService;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.ComptePersistanceAdapter;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.OperationPersistanceAdapter;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository.CompteRepository;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public CompteService compteService(ComptePersistanceAdapter comptePersistanceAdapter) {
        return new CompteService(comptePersistanceAdapter);
    }

    @Bean
    public ComptePersistanceAdapter comptePersistanceAdapter(CompteRepository compteRepository) {
        return new ComptePersistanceAdapter(compteRepository, modelMapper());
    }


    @Bean
    public OperationPersistanceAdapter operationPersistanceAdapter(OperationRepository operationRepository) {
        return new OperationPersistanceAdapter(operationRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

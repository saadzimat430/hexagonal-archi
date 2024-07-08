package com.exalt.it.bank.system.infrastructure.adapter.output.persistance;

import com.exalt.it.bank.system.application.port.out.OperationOutput;
import com.exalt.it.bank.system.domain.model.Operation;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.OperationEntity;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class OperationPersistanceAdapter implements OperationOutput {
    private final OperationRepository operationRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    @Override
    public Operation saveOperation(Operation operationDto) {
        OperationEntity operation = modelMapper.map(operationDto, OperationEntity.class);
        return modelMapper.map(operationRepository.save(operation), Operation.class);
    }
}

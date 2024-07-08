package com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository;

import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
}

package com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository;

import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.CompteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<CompteEntity, Long> {
}

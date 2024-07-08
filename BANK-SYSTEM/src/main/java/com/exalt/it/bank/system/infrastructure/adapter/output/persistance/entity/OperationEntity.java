package com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "operation")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "compte_id", referencedColumnName = "id")
    private CompteEntity compte;
    private String operationLibelle;
    private LocalDateTime localDateTime;
    private BigDecimal montant;

}

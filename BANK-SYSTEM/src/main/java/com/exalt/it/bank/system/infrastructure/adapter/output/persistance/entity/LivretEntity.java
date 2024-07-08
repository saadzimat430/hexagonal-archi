package com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Livret")

public class LivretEntity extends CompteEntity {

    private BigDecimal plafondDepot=BigDecimal.ZERO;

}

package com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity;

import com.exalt.it.bank.system.domain.enums.TypeCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "compte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",
        discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_active = true")
public class CompteEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected long id;
    protected String name;
    @Column(unique = true)
    protected String numeroCompte;
    protected BigDecimal solde=BigDecimal.ZERO;
    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<OperationEntity> operations;
    protected Boolean isActive = Boolean.TRUE;
    protected TypeCompte typeCompte;
    private BigDecimal plafondDecouvert;

}

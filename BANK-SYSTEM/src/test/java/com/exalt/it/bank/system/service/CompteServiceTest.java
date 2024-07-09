package com.exalt.it.bank.system.service;


import com.exalt.it.bank.system.config.BaseTest;
import com.exalt.it.bank.system.domain.enums.TypeCompte;
import com.exalt.it.bank.system.domain.exceptions.CompteNoFound;
import com.exalt.it.bank.system.domain.exceptions.OperationUnothorized;
import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.domain.model.Livret;
import com.exalt.it.bank.system.domain.model.ReleveBancaire;
import com.exalt.it.bank.system.domain.service.CompteService;
import com.exalt.it.bank.system.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompteServiceTest extends BaseTest {
    @Autowired
    private CompteService compteService;

    @BeforeEach
    public void setUp() {
    }

    @Order(1)
    @Test
    public void createCompteCourantOk() {

        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);
        //verifier que le compte est bien ajouté
        assertThat(response).isNotNull();
        assertThat(response.getNumeroCompte()).isNotNull();
        assertThat(response).extracting("name", "solde", "isActive", "typeCompte").containsExactly("EXALT", BigDecimal.valueOf(1000), true, TypeCompte.COURANT);

    }

    @Order(2)
    @Test
    public void createCompteLivretAndTestIfTypeCompteAndStatusAreGood() {

        Livret livret = TestUtils.getMockLivretToAdd();
        Compte response = compteService.createCompte(livret);
        //verifier que le compte Livret est bien ajouté
        assertThat(response).isNotNull();
        assertThat(response.getNumeroCompte()).isNotNull();
        assertThat(response).extracting("name", "solde", "isActive", "plafondDepot", "typeCompte").containsExactly("LIVRET", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(2000), TypeCompte.LIVRET);

    }

    @Order(3)
    @Test
    public void getCompteAddedAndCompteFetchedAreTheSame() {
        Compte compte = TestUtils.getMockCompteToAdd();
        compte = compteService.createCompte(compte);
        Compte response = compteService.getCompteById(compte.getId());
        assertThat(response).isNotNull();
        assertThat(response.getNumeroCompte()).isEqualTo(compte.getNumeroCompte());

    }

    @Order(4)
    @Test
    public void getAllComptesAdded() {
        List<Compte> response = compteService.getAllComptes();
        assertThat(response).isNotNull();
        assertThat(response.stream().filter(compte -> compte.getTypeCompte().equals(TypeCompte.LIVRET)).count()).isEqualTo(1);
        assertThat(response).hasSize(3);

    }

    @Order(5)
    @Test
    public void retraitCompteCourantOkEtMontantIsOk() {
        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);

        //le solde est 1000 et on veut retirer 100
        Compte responseRetrait = compteService.retraitArgent(BigDecimal.valueOf(100), response.getId());
        assertThat(responseRetrait).isNotNull();
        assertThat(responseRetrait.getSolde()).isEqualTo(BigDecimal.valueOf(1000).subtract(BigDecimal.valueOf(100).setScale(2)));

    }

    @Order(6)
    @Test
    public void retraitCompteCourantNokDepassementSolde() {
        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);

        //le solde est 1000 et on veut retirer 1001
        OperationUnothorized ex = assertThrowsExactly(OperationUnothorized.class, () -> compteService.retraitArgent(BigDecimal.valueOf(1001), response.getId()));
        assertThat(ex.getMessage()).isEqualTo("Operation non autorisée !");

    }

    @Order(7)
    @Test
    public void depotCompteCourantok() {
        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);
        Compte responseDepot = compteService.depotArgent(BigDecimal.valueOf(100), response.getId());
        assertThat(responseDepot).isNotNull();

        //verifier que le sold est egale a 1000 + 100
        assertThat(responseDepot.getSolde()).isEqualTo(BigDecimal.valueOf(1000).add(BigDecimal.valueOf(100)).setScale(2));

    }

    @Order(8)
    @Test
    public void depotCompteLivretCourantNokDepassementPlafondDepot() {
        Livret livret = TestUtils.getMockLivretToAdd();
        Livret response = (Livret) compteService.createCompte(livret);
        //le solde est 1000 et on veut deposer 2001
        OperationUnothorized ex = assertThrowsExactly(OperationUnothorized.class, () -> compteService.depotArgent(BigDecimal.valueOf(2001), response.getId()));
    }

    @Order(9)
    @Test
    public void depotCompteLivretCourantok() {
        Livret livret = TestUtils.getMockLivretToAdd();
        Livret response = (Livret) compteService.createCompte(livret);
        Livret responseDepot = (Livret) compteService.depotArgent(BigDecimal.valueOf(100), response.getId());
        assertThat(response).isNotNull();
        //verifier que le sold est egale a 1000 + 100
        assertThat(responseDepot.getSolde()).isEqualTo(BigDecimal.valueOf(1000).add(BigDecimal.valueOf(100)).setScale(2));

    }

    @Order(10)
    @Test
    public void deleteCompteOk() {
        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);
        Compte responseDeleted = compteService.deleteCompte(response.getId());
        assertThat(responseDeleted).isNotNull();
        //verifier que le compte est bien supprimé
        assertThat(responseDeleted.getIsActive()).isFalse();
        CompteNoFound ex = assertThrowsExactly(CompteNoFound.class, () -> compteService.getCompteById(response.getId()));
        assertThat(ex.getMessage()).isEqualTo("Compte avec id [" + response.getId() + "] n'existe pas.");

    }

    @Order(11)
    @Test
    public void addDecouvertOptionIsOkAndRetraitIsOkUnderPlafondDecouvert() {

        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);
        String responseDecouvert = compteService.activerDecouvert(response.getId(), BigDecimal.valueOf(200));
        assertThat(responseDecouvert).isEqualTo("Decouvert activé avec succès : [200]");
        Compte responseRetrait = compteService.retraitArgent(BigDecimal.valueOf(1100), response.getId());
        assertThat(responseRetrait).isNotNull();
        assertThat(responseRetrait.getSolde()).isEqualTo(BigDecimal.valueOf(-100).setScale(2));

    }

    @Order(12)
    @Test
    public void checkReleveIsCorrectAccordingToOperationsCalled() {

        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);
        compteService.depotArgent(BigDecimal.valueOf(100), response.getId());
        compteService.retraitArgent(BigDecimal.valueOf(50), response.getId());
        compteService.retraitArgent(BigDecimal.valueOf(10), response.getId());
        compteService.depotArgent(BigDecimal.valueOf(200), response.getId());
        compteService.retraitArgent(BigDecimal.valueOf(100), response.getId());
        compteService.depotArgent(BigDecimal.valueOf(100), response.getId());
        compteService.retraitArgent(BigDecimal.valueOf(50), response.getId());
        compteService.depotArgent(BigDecimal.valueOf(180), response.getId());
        ReleveBancaire releveBancaire = compteService.getReleveBancaire(response.getId());
        assertThat(releveBancaire).isNotNull();
        assertThat(releveBancaire.getOperations()).hasSize(8);

        //verifier que le releve bancaire est correct et l'ordre chronologie est bien respecté
        assertThat(releveBancaire.getOperations().get(0).getMontant()).isEqualTo(BigDecimal.valueOf(180).setScale(2));
        assertThat(releveBancaire.getOperations().get(1).getMontant()).isEqualTo(BigDecimal.valueOf(50).setScale(2));
        assertThat(releveBancaire.getOperations().get(2).getMontant()).isEqualTo(BigDecimal.valueOf(100).setScale(2));
        assertThat(releveBancaire.getOperations().get(3).getMontant()).isEqualTo(BigDecimal.valueOf(100).setScale(2));
        assertThat(releveBancaire.getOperations().get(4).getMontant()).isEqualTo(BigDecimal.valueOf(200).setScale(2));
        assertThat(releveBancaire.getOperations().get(5).getMontant()).isEqualTo(BigDecimal.valueOf(10).setScale(2));
        assertThat(releveBancaire.getOperations().get(6).getMontant()).isEqualTo(BigDecimal.valueOf(50).setScale(2));
        assertThat(releveBancaire.getOperations().get(7).getMontant()).isEqualTo(BigDecimal.valueOf(100).setScale(2));
        assertThat(releveBancaire.getSold()).isEqualTo(BigDecimal.valueOf(1370).setScale(2));


    }
    @Order(12)
    @Test
    public void checkNegativeDepositIsNotPermitted() {

        Compte compte = TestUtils.getMockCompteToAdd();
        Compte response = compteService.createCompte(compte);
        OperationUnothorized ex = assertThrowsExactly(OperationUnothorized.class, () -> compteService.depotArgent(BigDecimal.valueOf(-100), response.getId()));
        assertThat(ex.getMessage()).isEqualTo("Operation non autorisée !");
    }

}

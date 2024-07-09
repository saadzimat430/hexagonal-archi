package com.exalt.it.bank.system.controller;

import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.CompteEntity;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository.CompteRepository;
import com.exalt.it.bank.system.utils.TestUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@RequiredArgsConstructor
public class CompteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CompteRepository compteRepository;

    @BeforeAll
    static void setUp() {

    }

    @Test
    void testGetAllComptesIsOk() throws Exception {

        when(compteRepository.findAll()).thenReturn(TestUtils.getMockComptes());

        this.mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.GET_COMPTES_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void testGetCompteByIdIsOk() throws Exception {

        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());

        this.mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.GET_COMPTE_URL + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void testGetCompteByIdIsNotFound() throws Exception {

        when(compteRepository.findById(any())).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.GET_COMPTE_URL + 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testAddCompteIsOk() throws Exception {

        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());

        this.mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.POST_COMPTE_URL)
                        .content(TestUtils.convertJsonToString(TestUtils.getMockCompteRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
        verify(compteRepository, times(1)).save(Mockito.any(CompteEntity.class));

    }

    @Test
    void testAddCompteLivretIsOk() throws Exception {

        when(compteRepository.save(any())).thenReturn(TestUtils.getMockLivretCompte().get());

        this.mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.POST_LIVRET_URL)
                        .content(TestUtils.convertJsonToString(TestUtils.getMockCompteRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
        verify(compteRepository, times(1)).save(Mockito.any(CompteEntity.class));

    }

    @Test
    void testAddDecouvertOptionIsOk() throws Exception {

        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());

        this.mockMvc.perform(MockMvcRequestBuilders.put(TestUtils.PUT_DECOUVERT_URL, "1")
                        .param("plafondDecouvert", "300")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isString());
        verify(compteRepository, times(1)).save(Mockito.any(CompteEntity.class));

    }


    @Test
    void testAddDecouvertOptionIsNOkDecouvertNegatif() throws Exception {

        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());

        this.mockMvc.perform(MockMvcRequestBuilders.put(TestUtils.PUT_DECOUVERT_URL, "1")
                        .param("plafondDecouvert", "-300")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Le plafond de découvert doit être supérieur à 0"));
        verify(compteRepository, times(0)).save(Mockito.any(CompteEntity.class));

    }

    @Test
    void testAddDecouvertOptionIsNOkSoldNegatif() throws Exception {
        CompteEntity compteEntity = TestUtils.getMockCompte().get();
        compteEntity.setSolde(BigDecimal.valueOf(-200));
        when(compteRepository.findById(any())).thenReturn(Optional.of(compteEntity));
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());

        this.mockMvc.perform(MockMvcRequestBuilders.put(TestUtils.PUT_DECOUVERT_URL, "1")
                        .param("plafondDecouvert", "300")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Vous devez neutralisez votre sold avant d'agrandir le decouvert, votre solde est est [" + -200 + "]"));
        verify(compteRepository, times(0)).save(Mockito.any(CompteEntity.class));

    }

    @Test
    void testDeleteCompteIsOk() throws Exception {

        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());

        this.mockMvc.perform(MockMvcRequestBuilders.delete(TestUtils.DELETE_COMPTE, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(compteRepository, times(1)).save(Mockito.any(CompteEntity.class));

    }

    @Test
    void testReleveBancaireIsOk() throws Exception {

        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());

        this.mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.RELEVE_BANCAIRE_URL, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

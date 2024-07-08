package com.exalt.it.bank.system.controller;


import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.entity.CompteEntity;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository.CompteRepository;
import com.exalt.it.bank.system.infrastructure.adapter.output.persistance.repository.OperationRepository;
import com.exalt.it.bank.system.utils.TestUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@RequiredArgsConstructor
public class OperationontrollerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OperationRepository operationRepository;
    @MockBean
    private CompteRepository compteRepository;

    @Test
    void testRetraitArgentIsOk() throws Exception {

        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());
        when(operationRepository.save(any())).thenReturn(TestUtils.getMockOperation());

        this.mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.RETRAIT_ARGENT_URL, 1L)
                        .param("argent", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRetraitArgentIsNOkDepasseSold() throws Exception {
        CompteEntity compteEntity = TestUtils.getMockCompte().get();
        compteEntity.setPlafondDecouvert(BigDecimal.valueOf(0));

        when(compteRepository.findById(any())).thenReturn(Optional.of(compteEntity));
        when(compteRepository.save(any())).thenReturn(compteEntity);
        when(operationRepository.save(any())).thenReturn(TestUtils.getMockOperation());

        this.mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.RETRAIT_ARGENT_URL, 1L)
                        .param("argent", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Operation non autorisée !"));
    }

    @Test
    void testRetraitArgentIsNOkDepassePlafondDecouvert() throws Exception {
        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());
        when(operationRepository.save(any())).thenReturn(TestUtils.getMockOperation());

        this.mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.RETRAIT_ARGENT_URL, 1L)
                        .param("argent", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Operation non autorisée !"));
    }

    @Test
    void testDepotOkCompteCourant() throws Exception {
        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockCompte());
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockCompte().get());
        when(operationRepository.save(any())).thenReturn(TestUtils.getMockOperation());

        this.mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.DEPOT_ARGENT_URL, 1L)
                        .param("argent", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void testDepotNOkCompteLivretDepassementPlafond() throws Exception {
        when(compteRepository.findById(any())).thenReturn(TestUtils.getMockLivretCompte());
        when(compteRepository.save(any())).thenReturn(TestUtils.getMockLivretCompte().get());
        when(operationRepository.save(any())).thenReturn(TestUtils.getMockOperation());

        this.mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.DEPOT_ARGENT_URL, 1L)
                        .param("argent", "1200")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Operation non autorisée !"));
    }
}

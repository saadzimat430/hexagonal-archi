package com.exalt.it.bank.system.infrastructure.adapter.input.rest.controller;

import com.exalt.it.bank.system.application.port.input.CompteInput;
import com.exalt.it.bank.system.domain.model.Compte;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.data.response.CompteReponse;
import com.exalt.it.bank.system.infrastructure.adapter.input.rest.mapper.CompteRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationRestAdapter {

    private final CompteInput compteInput;
    private final CompteRestMapper compteRestMapper = new CompteRestMapper();

    @PostMapping("/retrait/{compteId}")
    public CompteReponse retraitArgent(@PathVariable Long compteId, @RequestParam("argent") BigDecimal argent) {

        Compte compte = compteInput.retraitArgent(argent, compteId);

        return compteRestMapper.toCompteResponse(compte);
    }

    @PostMapping("/depot/{compteId}")
    public CompteReponse depotArgent(@PathVariable Long compteId, @RequestParam("argent") BigDecimal argent) {

        Compte compte = compteInput.depotArgent(argent, compteId);

        return compteRestMapper.toCompteResponse(compte);
    }


}

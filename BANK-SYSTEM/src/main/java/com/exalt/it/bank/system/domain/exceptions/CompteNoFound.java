package com.exalt.it.bank.system.domain.exceptions;

public class CompteNoFound extends RuntimeException {
    public CompteNoFound(String message) {
        super(message);
    }
}

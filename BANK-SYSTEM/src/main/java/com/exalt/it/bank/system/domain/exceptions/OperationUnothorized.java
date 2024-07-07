package com.exalt.it.bank.system.domain.exceptions;

public class OperationUnothorized extends RuntimeException{
    public OperationUnothorized(String message) {
        super(message);
    }

}

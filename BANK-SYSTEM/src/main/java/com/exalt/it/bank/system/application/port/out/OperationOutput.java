package com.exalt.it.bank.system.application.port.out;

import com.exalt.it.bank.system.domain.model.Operation;

public interface OperationOutput {
    Operation saveOperation(Operation operation);

}

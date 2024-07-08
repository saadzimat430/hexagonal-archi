package com.exalt.it.bank.system.infrastructure.adapter.error.exceptionHandlers;


import com.exalt.it.bank.system.domain.exceptions.CompteNoFound;
import com.exalt.it.bank.system.domain.exceptions.OperationUnothorized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdapter extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler(CompteNoFound.class)
    public final ResponseEntity<Object> handleUserNotFoundException(CompteNoFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OperationUnothorized.class)
    public final ResponseEntity<Object> handleOperationUnortorizedException(OperationUnothorized ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }
}

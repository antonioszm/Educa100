package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotaNotFoundException extends NotFoundException{
    public NotaNotFoundException(Long id){
        super("Nota com o id " +id +" não encontrado");
    }
}

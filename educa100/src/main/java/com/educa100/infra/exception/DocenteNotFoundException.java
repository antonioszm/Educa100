package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocenteNotFoundException extends NotFoundException{
    public DocenteNotFoundException(Long id){
        super("Docente com o id " +id +" não encontrado");
    }
}

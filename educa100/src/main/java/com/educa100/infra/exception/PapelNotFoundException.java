package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PapelNotFoundException extends NotFoundException{
    public PapelNotFoundException(Long id){
        super("Papel com o id " +id +" não encontrado");
    }
}

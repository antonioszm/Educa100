package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TurmaNotFoundException extends NotFoundException{
    public TurmaNotFoundException(Long id){
        super("Turma com o id " +id +" não encontrado");
    }
}

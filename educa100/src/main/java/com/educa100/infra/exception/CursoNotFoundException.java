package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CursoNotFoundException extends NotFoundException{
    public CursoNotFoundException(Long id){
        super("Curso com o id " +id +" não encontrado");
    }
}

package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MateriaNotFoundException extends NotFoundException{
    public MateriaNotFoundException(Long id){
        super("Materia com o id " +id +" não encontrado");
    }
}

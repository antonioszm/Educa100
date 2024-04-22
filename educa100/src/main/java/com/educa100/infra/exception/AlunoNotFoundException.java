package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlunoNotFoundException extends NotFoundException{
    public AlunoNotFoundException(Long id){
        super("Aluno com o id " +id +" não encontrado");
    }
}

package com.educa100.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends NotFoundException{
    public UsuarioNotFoundException(Long id){
        super("Usuario com o id " +id +" não encontrado");
    }
}

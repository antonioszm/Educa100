package com.educa100.infra.exception;

public class UsuarioNotFoundException extends NotFoundException{
    public UsuarioNotFoundException(Long id){
        super("Usuario com o id " +id +" não encontrado");
    }
}

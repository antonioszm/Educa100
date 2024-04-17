package com.educa100.infra.exception;

public class PapelNotFoundException extends NotFoundException{
    public PapelNotFoundException(Long id){
        super("Papel com o id " +id +" não encontrado");
    }
}

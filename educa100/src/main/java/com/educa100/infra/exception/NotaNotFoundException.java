package com.educa100.infra.exception;

public class NotaNotFoundException extends NotFoundException{
    public NotaNotFoundException(int id){
        super("Nota com o id " +id +" não encontrado");
    }
}

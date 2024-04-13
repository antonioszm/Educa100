package com.educa100.infra.exception;

public class DocenteNotFoundException extends NotFoundException{
    public DocenteNotFoundException(int id){
        super("Docente com o id " +id +" não encontrado");
    }
}

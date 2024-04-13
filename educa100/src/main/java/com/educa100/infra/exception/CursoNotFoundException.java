package com.educa100.infra.exception;

public class CursoNotFoundException extends NotFoundException{
    public CursoNotFoundException(int id){
        super("Curso com o id " +id +" não encontrado");
    }
}

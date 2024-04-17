package com.educa100.infra.exception;

public class CursoNotFoundException extends NotFoundException{
    public CursoNotFoundException(Long id){
        super("Curso com o id " +id +" não encontrado");
    }
}

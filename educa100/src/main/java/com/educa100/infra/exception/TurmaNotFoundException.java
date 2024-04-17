package com.educa100.infra.exception;

public class TurmaNotFoundException extends NotFoundException{
    public TurmaNotFoundException(Long id){
        super("Turma com o id " +id +" não encontrado");
    }
}

package com.educa100.infra.exception;

public class AlunoNotFoundException extends NotFoundException{
    public AlunoNotFoundException(Long id){
        super("Aluno com o id " +id +" não encontrado");
    }
}

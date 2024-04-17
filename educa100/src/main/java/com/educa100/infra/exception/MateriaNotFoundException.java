package com.educa100.infra.exception;

public class MateriaNotFoundException extends NotFoundException{
    public MateriaNotFoundException(Long id){
        super("Materia com o id " +id +" não encontrado");
    }
}

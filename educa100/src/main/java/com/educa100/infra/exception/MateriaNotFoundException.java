package com.educa100.infra.exception;

public class MateriaNotFoundException extends NotFoundException{
    public MateriaNotFoundException(int id){
        super("Materia com o id " +id +" não encontrado");
    }
}

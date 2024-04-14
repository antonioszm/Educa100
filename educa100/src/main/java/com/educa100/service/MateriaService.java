package com.educa100.service;

import com.educa100.datasource.entity.MateriaEntity;

import java.util.List;

public interface MateriaService {
    public MateriaEntity salvar(MateriaEntity materia);
    public void atualizar(int id);
    public void removerPorId(int id);
    public List<MateriaEntity> listarTodos();
    public MateriaEntity listarPorId(int id);
    public List<MateriaEntity> listarPorIdCurso(int id);

}
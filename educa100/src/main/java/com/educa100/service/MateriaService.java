package com.educa100.service;

import com.educa100.datasource.entity.MateriaEntity;

import java.util.List;

public interface MateriaService {
    public MateriaEntity salvar(MateriaEntity materia);
    public void atualizar(Long id);
    public void removerPorId(Long id);
    public List<MateriaEntity> listarTodos();
    public MateriaEntity listarPorId(Long id);
    public List<MateriaEntity> listarPorIdCurso(Long id);

}
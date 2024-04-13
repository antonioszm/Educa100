package com.educa100.service;

import com.educa100.datasource.entity.CursoEntity;

import java.util.List;

public interface CursoService {
    public CursoEntity salvar(CursoEntity curso);
    public void atualizar(int id);
    public void removerPorId(int id);
    public List<CursoEntity> listarTodos();
    public CursoEntity listarPorId(int id);
}
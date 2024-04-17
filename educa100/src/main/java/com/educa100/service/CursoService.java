package com.educa100.service;

import com.educa100.datasource.entity.CursoEntity;

import java.util.List;

public interface CursoService {
    public CursoEntity salvar(CursoEntity curso);
    public void atualizar(Long id);
    public void removerPorId(Long id);
    public List<CursoEntity> listarTodos();
    public CursoEntity listarPorId(Long id);
}
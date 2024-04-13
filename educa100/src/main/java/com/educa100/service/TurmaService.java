package com.educa100.service;

import com.educa100.datasource.entity.TurmaEntity;

import java.util.List;

public interface TurmaService {
    public TurmaEntity salvar(TurmaEntity turma);
    public void atualizar(int id);
    public void removerPorId(int id);
    public List<TurmaEntity> listarTodos();
    public TurmaEntity listarPorId(int id);
}
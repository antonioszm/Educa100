package com.educa100.service;

import com.educa100.datasource.entity.TurmaEntity;

import java.util.List;

public interface TurmaService {
    public TurmaEntity salvar(TurmaEntity turma);
    public void atualizar(Long id);
    public void removerPorId(Long id);
    public List<TurmaEntity> listarTodos();
    public TurmaEntity listarPorId(Long id);
}
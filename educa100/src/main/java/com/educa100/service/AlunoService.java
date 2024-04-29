package com.educa100.service;

import com.educa100.datasource.entity.AlunoEntity;

import java.util.List;

public interface AlunoService {
    public AlunoEntity salvar(AlunoEntity aluno);
    public void atualizar(Long id);
    public void removerPorId(Long id);
    public List<AlunoEntity> listarTodos();
    public AlunoEntity listarPorId(Long id);
}
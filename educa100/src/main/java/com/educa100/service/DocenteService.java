package com.educa100.service;

import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.repository.DocenteRepository;

import java.util.List;

public interface DocenteService {
    public DocenteEntity salvar(DocenteEntity docente);
    public void atualizar(Long id);
    public void removerPorId(Long id);
    public List<DocenteEntity> listarTodos();
    public DocenteEntity listarPorId(Long id);
}
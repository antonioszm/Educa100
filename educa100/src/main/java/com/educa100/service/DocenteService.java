package com.educa100.service;

import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.repository.DocenteRepository;

import java.util.List;

public interface DocenteService {
    public DocenteEntity salvar(DocenteEntity docente);
    public int atualizar(DocenteEntity docente);
    public void removerPorId(int id);
    public List<DocenteEntity> listarTodos();
    public DocenteEntity listarPorId(int id);
}
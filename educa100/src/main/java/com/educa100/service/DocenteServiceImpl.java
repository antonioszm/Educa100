package com.educa100.service;

import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.repository.DocenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocenteServiceImpl implements DocenteService{

    private final DocenteRepository repository;

    public DocenteServiceImpl(DocenteRepository repository) {
        this.repository = repository;
    }

    @Override
    public DocenteEntity salvar(DocenteEntity docente) {
        docente.setId(0);
        return repository.save(docente);
    }

    @Override
    public int atualizar(DocenteEntity docente) {
        return 0;
    }

    @Override
    public void removerPorId(int id) {

    }

    @Override
    public List<DocenteEntity> listarTodos() {
        return null;
    }

    @Override
    public DocenteEntity listarPorId(int id) {
        return null;
    }
}

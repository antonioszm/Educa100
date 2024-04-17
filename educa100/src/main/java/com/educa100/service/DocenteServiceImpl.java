package com.educa100.service;

import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.repository.DocenteRepository;
import com.educa100.infra.exception.DocenteNotFoundException;
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
        docente.setId(null);
        return repository.save(docente);
    }

    @Override
    public void atualizar(Long id) {
        DocenteEntity docente = listarPorId(id);
        repository.update(docente.getId(),docente.getNome(),docente.getData_entrada(),docente.getId_usuario());
    }

    @Override
    public void removerPorId(Long id) {
        DocenteEntity docente = listarPorId(id);
        repository.delete(docente);
    }

    @Override
    public List<DocenteEntity> listarTodos() {
        return repository.findAll();
    }

    @Override
    public DocenteEntity listarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new DocenteNotFoundException(id));
    }
}

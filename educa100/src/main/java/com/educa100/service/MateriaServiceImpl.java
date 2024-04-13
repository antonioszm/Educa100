package com.educa100.service;

import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.repository.MateriaRepository;
import com.educa100.infra.exception.MateriaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService{

    private final MateriaRepository repository;

    public MateriaServiceImpl(MateriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MateriaEntity salvar(MateriaEntity materia) {
        materia.setId(0);
        return repository.save(materia);
    }

    @Override
    public void atualizar(int id) {
        MateriaEntity materia = listarPorId(id);
        repository.update(materia.getId(),materia.getNome(), materia.getId_curso());
    }

    @Override
    public void removerPorId(int id) {
        MateriaEntity materia = listarPorId(id);
        repository.delete(materia);
    }

    @Override
    public List<MateriaEntity> listarTodos() {
        return repository.findAll();
    }

    @Override
    public MateriaEntity listarPorId(int id) {
        return repository.findById(id).orElseThrow(() -> new MateriaNotFoundException(id));
    }
}

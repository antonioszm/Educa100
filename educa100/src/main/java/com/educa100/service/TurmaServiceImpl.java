package com.educa100.service;

import com.educa100.datasource.entity.TurmaEntity;
import com.educa100.datasource.repository.TurmaRepository;
import com.educa100.infra.exception.TurmaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaServiceImpl implements TurmaService{

    private final TurmaRepository repository;

    public TurmaServiceImpl(TurmaRepository repository) {
        this.repository = repository;
    }

    @Override
    public TurmaEntity salvar(TurmaEntity turma) {
        turma.setId(0);
        return repository.save(turma);
    }

    @Override
    public void atualizar(TurmaEntity turma) {
        repository.update(turma.getId(),turma.getNome(), turma.getAlunos(), turma.getProfessor(), turma.getId_curso());
    }

    @Override
    public void removerPorId(int id) {
        TurmaEntity turma = listarPorId(id);
        repository.delete(turma);
    }

    @Override
    public List<TurmaEntity> listarTodos() {
        return repository.findAll();
    }

    @Override
    public TurmaEntity listarPorId(int id) {
        return repository.findById(id).orElseThrow(() -> new TurmaNotFoundException(id));
    }
}

package com.educa100.service;

import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.repository.AlunoRepository;
import com.educa100.infra.exception.AlunoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoServiceImpl implements AlunoService{

    private final AlunoRepository repository;

    public AlunoServiceImpl(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public AlunoEntity salvar(AlunoEntity aluno) {
        aluno.setId(0);
        return repository.save(aluno);
    }

    @Override
    public void atualizar(AlunoEntity aluno) {
        repository.update(aluno.getId(),aluno.getNome(), aluno.getData_nascimento(), aluno.getId_usuario(), aluno.getId_turma());
    }

    @Override
    public void removerPorId(int id) {
        AlunoEntity aluno = listarPorId(id);
        repository.delete(aluno);
    }

    @Override
    public List<AlunoEntity> listarTodos() {
        return repository.findAll();
    }

    @Override
    public AlunoEntity listarPorId(int id) {
        return repository.findById(id).orElseThrow(() -> new AlunoNotFoundException(id));
    }
}

package com.educa100.facade;

import com.educa100.controller.dto.request.CursoRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.CursoResponse;
import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import com.educa100.service.CursoServiceImpl;
import com.educa100.service.MateriaServiceImpl;
import com.educa100.service.TurmaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CursoFacade {

    private final CursoServiceImpl service;

    private final TurmaServiceImpl turmaService;

    private final MateriaServiceImpl materiaService;

    public CursoFacade(CursoServiceImpl service, TurmaServiceImpl turmaService, MateriaServiceImpl materiaService) {
        this.service = service;
        this.turmaService = turmaService;
        this.materiaService = materiaService;
    }
    public CursoEntity criarCursos(@RequestBody CursoRequest request, JwtAuthenticationToken jwt){

        CursoEntity curso = new CursoEntity();
        if (!request.nome().isBlank()){
            curso.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome invalido");
        }
        List<TurmaEntity> listaGeralDeTurmas = turmaService.listarTodos();
        List<TurmaEntity> listaDeTurmas = new ArrayList<>();
        for (TurmaEntity turma : listaGeralDeTurmas){
            if (turma.getId_curso().equals(curso.getId())){
                listaDeTurmas.add(turma);
            }
        }
        curso.setTurmas(listaDeTurmas);
        List<MateriaEntity> listaGeralDeMaterias = materiaService.listarTodos();
        List<MateriaEntity> listaDeMaterias = new ArrayList<>();
        for (MateriaEntity materia : listaGeralDeMaterias){
            if (materia.getId_curso().equals(curso.getId())){
                listaDeMaterias.add(materia);
            }
        }
        curso.setMaterias(listaDeMaterias);
        service.salvar(curso);
        return curso;
    }

    public CursoEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        CursoEntity curso = service.listarPorId(id);
        return curso;
    }

    public CursoEntity atualizar(Long id, TurmaRequest request, JwtAuthenticationToken jwt){
        CursoEntity curso = service.listarPorId(id);
        if (!request.nome().isBlank()){
            curso.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome invalido");
        }
        List<TurmaEntity> listaGeralDeTurmas = turmaService.listarTodos();
        List<TurmaEntity> listaDeTurmas = new ArrayList<>();
        for (TurmaEntity turma : listaGeralDeTurmas){
            if (turma.getId_curso().equals(curso.getId())){
                listaDeTurmas.add(turma);
            }
        }
        curso.setTurmas(listaDeTurmas);
        List<MateriaEntity> listaGeralDeMaterias = materiaService.listarTodos();
        List<MateriaEntity> listaDeMaterias = new ArrayList<>();
        for (MateriaEntity materia : listaGeralDeMaterias){
            if (materia.getId_curso().equals(curso.getId())){
                listaDeMaterias.add(materia);
            }
        }
        curso.setMaterias(listaDeMaterias);
        service.atualizar(curso.getId());
        return curso;
    }
    public void deletar(Long id){
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<CursoEntity>> listarTodos(JwtAuthenticationToken jwt){
        List<CursoEntity> listaCursos = service.listarTodos();
        if (listaCursos.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaCursos);
    }
}

package com.educa100.controller;

import com.educa100.controller.dto.request.CursoRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.CursoResponse;
import com.educa100.controller.dto.response.TurmaResponse;
import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import com.educa100.service.CursoServiceImpl;
import com.educa100.service.DocenteServiceImpl;
import com.educa100.service.MateriaServiceImpl;
import com.educa100.service.TurmaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoServiceImpl service;

    private final TurmaServiceImpl turmaService;

    private final MateriaServiceImpl materiaService;

    public CursoController(CursoServiceImpl service, TurmaServiceImpl turmaService, MateriaServiceImpl materiaService) {
        this.service = service;
        this.turmaService = turmaService;
        this.materiaService = materiaService;
    }


    @PostMapping
    public ResponseEntity<CursoResponse> criarCursos(@RequestBody CursoRequest request){

        CursoEntity curso = new CursoEntity();
        curso.setNome(request.nome());
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

        return ResponseEntity.created(null).body(new CursoResponse(curso.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoEntity> listarPorId(@PathVariable Long id){
        CursoEntity curso = service.listarPorId(id);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoEntity> atualizar(@PathVariable Long id, @RequestBody TurmaRequest request){
        CursoEntity curso = service.listarPorId(id);
        curso.setNome(request.nome());
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
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<CursoEntity>> listarTodos(){
        List<CursoEntity> listaCursos = service.listarTodos();
        if (listaCursos.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaCursos);
    }
}

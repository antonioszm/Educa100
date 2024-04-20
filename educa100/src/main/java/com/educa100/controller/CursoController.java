package com.educa100.controller;

import com.educa100.controller.dto.request.CursoRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.CursoResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.CursoFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cursos")
@Slf4j
public class CursoController {

    private final CursoFacade facade;

    public CursoController(CursoFacade facade) {
        this.facade = facade;
    }


    @PostMapping
    public ResponseEntity<CursoResponse> criarCursos(@RequestBody CursoRequest request, JwtAuthenticationToken jwt){
        CursoEntity aluno = facade.criarCursos(request, jwt);
        log.info("Curso cirado com sucesso!");
        return ResponseEntity.created(null).body(new CursoResponse(aluno.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoEntity> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt){
        log.info("Curso com {id} foi listado" + id);
        CursoEntity curso = facade.listarPorId(id, jwt);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoEntity> atualizar(@PathVariable Long id, @RequestBody TurmaRequest request, JwtAuthenticationToken jwt){
        CursoEntity curso = facade.atualizar(id, request, jwt);
        log.info("Curso atualizado com sucesso!");
        return ResponseEntity.ok(curso);
    }

    @GetMapping
    public ResponseEntity<List<CursoEntity>> listarTodos(JwtAuthenticationToken jwt){
        List<CursoEntity> listaDeCursos = facade.listarTodos(jwt);
        log.info("Todos os cursos listados com sucesso!");
        return ResponseEntity.ok(listaDeCursos);
    }

    @GetMapping("/{id_curso}/materias")
    public ResponseEntity<List<MateriaEntity>> listaCursoId(@PathVariable Long id){
        List<MateriaEntity> listaMaterias = facade.listaCursoId(id);
        log.info("Todos os materias do curso "+id+" listados com sucesso!");
        return ResponseEntity.ok(listaMaterias);
    }
}

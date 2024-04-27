package com.educa100.controller;

import com.educa100.controller.dto.request.CursoRequest;
import com.educa100.controller.dto.response.CursoResponse;
import com.educa100.controller.dto.response.MateriaResponse;
import com.educa100.controller.dto.response.creation.CursoCreationResponse;
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
    public ResponseEntity<CursoCreationResponse> criarCursos(@RequestBody CursoRequest request, JwtAuthenticationToken jwt){
        CursoEntity aluno = facade.criarCursos(request, jwt);
        log.info("Curso cirado com sucesso!");
        return ResponseEntity.created(null).body(new CursoCreationResponse(aluno.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt){
        log.info("Curso com {id} foi listado" + id);
        CursoEntity curso = facade.listarPorId(id, jwt);
        List<TurmaEntity> listaDeTurma = curso.getTurmas();
        List<Long> turmasId = new ArrayList<>();
        for (TurmaEntity turma:listaDeTurma){
            turmasId.add(turma.getId());
        }
        List<MateriaEntity> listaDeMaterias = curso.getMaterias();
        List<Long> materiasId = new ArrayList<>();
        for (MateriaEntity materias:listaDeMaterias){
            materiasId.add(materias.getId());
        }
        return ResponseEntity.ok(new CursoResponse(curso.getId(),curso.getNome(),turmasId,materiasId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> atualizar(@PathVariable Long id, @RequestBody CursoRequest request, JwtAuthenticationToken jwt){
        CursoEntity curso = facade.atualizar(id, request, jwt);
        log.info("Curso atualizado com sucesso!");
        List<TurmaEntity> listaDeTurma = curso.getTurmas();
        List<Long> turmasId = new ArrayList<>();
        for (TurmaEntity turma:listaDeTurma){
            turmasId.add(turma.getId());
        }
        List<MateriaEntity> listaDeMaterias = curso.getMaterias();
        List<Long> materiasId = new ArrayList<>();
        for (MateriaEntity materias:listaDeMaterias){
            materiasId.add(materias.getId());
        }
        return ResponseEntity.ok(new CursoResponse(curso.getId(),curso.getNome(),turmasId,materiasId));
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listarTodos(JwtAuthenticationToken jwt){
        List<CursoEntity> listaDeCursos = facade.listarTodos(jwt);
        log.info("Todos os cursos listados com sucesso!");
        List<CursoResponse> listaDeCursosDto = new ArrayList<>();
        for (CursoEntity curso : listaDeCursos){
            List<TurmaEntity> listaDeTurma = curso.getTurmas();
            List<Long> turmasId = new ArrayList<>();
            for (TurmaEntity turma:listaDeTurma){
                turmasId.add(turma.getId());
            }
            List<MateriaEntity> listaDeMaterias = curso.getMaterias();
            List<Long> materiasId = new ArrayList<>();
            for (MateriaEntity materias:listaDeMaterias){
                materiasId.add(materias.getId());
            }
            CursoResponse dto = new CursoResponse(curso.getId(),curso.getNome(),turmasId,materiasId);
            listaDeCursosDto.add(dto);
        }
        return ResponseEntity.ok(listaDeCursosDto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id,JwtAuthenticationToken jwt) throws Exception {
        facade.deletar(id, jwt);
        log.info("Curso deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id_curso}/materias")
    public ResponseEntity<List<MateriaResponse>> listarMateriasPorIdCurso(@PathVariable Long id_curso){
        List<MateriaEntity> listaMaterias = facade.listarMateriasPorIdCurso(id_curso);
        List<MateriaResponse> listaMateriasDto = new ArrayList<>();
        for (MateriaEntity materia : listaMaterias){
            MateriaResponse dto = new MateriaResponse(materia.getId(), materia.getNome(), materia.getId_curso().getId());
            listaMateriasDto.add(dto);
        }
        log.info("Todos os materias do curso "+id_curso+" listados com sucesso!");
        return ResponseEntity.ok(listaMateriasDto);
    }
}

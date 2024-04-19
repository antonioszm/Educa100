package com.educa100.controller;

import com.educa100.controller.dto.request.CursoRequest;
import com.educa100.controller.dto.request.MateriaRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.CursoResponse;
import com.educa100.controller.dto.response.MateriaResponse;
import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.service.CursoServiceImpl;
import com.educa100.service.MateriaService;
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
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaServiceImpl service;

    private final CursoServiceImpl cursoService;


    public MateriaController(MateriaServiceImpl service, CursoServiceImpl cursoService) {
        this.service = service;
        this.cursoService = cursoService;
    }


    @PostMapping
    public ResponseEntity<MateriaResponse> criarCursos(@RequestBody MateriaRequest request){

        MateriaEntity materia = new MateriaEntity();
        materia.setNome(request.nome());
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }
        materia.setId_curso(cursoValido);
        service.salvar(materia);

        return ResponseEntity.created(null).body(new MateriaResponse(materia.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaEntity> listarPorId(@PathVariable Long id){
        MateriaEntity materia = service.listarPorId(id);
        return ResponseEntity.ok(materia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaEntity> atualizar(@PathVariable Long id, @RequestBody TurmaRequest request){
        MateriaEntity materia = service.listarPorId(id);
        materia.setNome(request.nome());
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }
        materia.setId_curso(cursoValido);
        service.atualizar(materia.getId());
        return ResponseEntity.ok(materia);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<MateriaEntity>> listarTodos(){
        List<MateriaEntity> listaMaterias = service.listarTodos();
        if (listaMaterias.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaMaterias);
    }

    @GetMapping("cursos/{id_curso}/materias")
    public ResponseEntity<List<MateriaEntity>> listaCursoId(@PathVariable Long id){
        List<MateriaEntity> listaMaterias = service.listarPorIdCurso(id);
        if (listaMaterias.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaMaterias);
    }
}

package com.educa100.controller;

import com.educa100.controller.dto.request.MateriaRequest;
import com.educa100.controller.dto.request.NotaRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.MateriaResponse;
import com.educa100.controller.dto.response.NotaResponse;
import com.educa100.datasource.entity.*;
import com.educa100.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notas")
public class NotasController {

    private final NotaServiceImpl service;

    private final AlunoServiceImpl alunoService;

    private final DocenteServiceImpl docenteService;

    private final MateriaServiceImpl materiaService;


    public NotasController(NotaServiceImpl service, AlunoServiceImpl alunoService, DocenteServiceImpl docenteService, MateriaServiceImpl materiaService) {
        this.service = service;
        this.alunoService = alunoService;
        this.docenteService = docenteService;
        this.materiaService = materiaService;
    }


    @PostMapping
    public ResponseEntity<NotaResponse> criarNotas(@RequestBody NotaRequest request){

        NotaEntity nota = new NotaEntity();

        Optional<AlunoEntity> aluno = Optional.ofNullable(alunoService.listarPorId(request.id_aluno()));
        AlunoEntity alunoValido = null;
        if (aluno.isPresent()){
            alunoValido = aluno.get();
        }
        nota.setId_aluno(alunoValido);

        Optional<DocenteEntity> professor = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (professor.isPresent()){
            docenteValido = professor.get();
        }
        nota.setId_professor(docenteValido);

        Optional<MateriaEntity> materia = Optional.ofNullable(materiaService.listarPorId(request.id_materia()));
        MateriaEntity materiaValido = null;
        if (materia.isPresent()){
            materiaValido = materia.get();
        }
        nota.setId_materia(materiaValido);

        nota.setData(request.data());
        nota.setValor(request.valor());

        service.salvar(nota);

        return ResponseEntity.created(null).body(new NotaResponse(nota.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaEntity> listarPorId(@PathVariable Long id){
        NotaEntity nota = service.listarPorId(id);
        return ResponseEntity.ok(nota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaEntity> atualizar(@PathVariable Long id, @RequestBody NotaRequest request){
        NotaEntity nota = service.listarPorId(id);

        Optional<AlunoEntity> aluno = Optional.ofNullable(alunoService.listarPorId(request.id_aluno()));
        AlunoEntity alunoValido = null;
        if (aluno.isPresent()){
            alunoValido = aluno.get();
        }
        nota.setId_aluno(alunoValido);

        Optional<DocenteEntity> professor = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (professor.isPresent()){
            docenteValido = professor.get();
        }
        nota.setId_professor(docenteValido);

        Optional<MateriaEntity> materia = Optional.ofNullable(materiaService.listarPorId(request.id_materia()));
        MateriaEntity materiaValido = null;
        if (materia.isPresent()){
            materiaValido = materia.get();
        }
        nota.setId_materia(materiaValido);

        nota.setData(request.data());
        nota.setValor(request.valor());

        service.atualizar(nota.getId());
        return ResponseEntity.ok(nota);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<NotaEntity>> listarTodos(){
        List<NotaEntity> listaNotas = service.listarTodos();
        if (listaNotas.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaNotas);
    }

    @GetMapping("alunos/{id_aluno}/notas")
    public ResponseEntity<List<NotaEntity>> listaAlunoId(@PathVariable Long id_aluno){
        List<NotaEntity> listaNotas = service.listarPorIdAluno(id_aluno);
        if (listaNotas.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaNotas);
    }
}

package com.educa100.controller;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.TurmaResponse;
import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.NotaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import com.educa100.infra.exception.AlunoNotFoundException;
import com.educa100.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaServiceImpl service;

    private final DocenteServiceImpl docenteService;

    private final CursoServiceImpl cursoService;

    private final AlunoServiceImpl alunoService;

    private final NotaServiceImpl notaService;

    public TurmaController(TurmaServiceImpl service, DocenteServiceImpl docenteService, CursoServiceImpl cursoService, AlunoServiceImpl alunoService, NotaServiceImpl notaService) {
        this.service = service;
        this.docenteService = docenteService;
        this.cursoService = cursoService;
        this.alunoService = alunoService;
        this.notaService = notaService;
    }

    @PostMapping
    public ResponseEntity<TurmaResponse> criarTurma(@RequestBody TurmaRequest request){

        TurmaEntity turma = new TurmaEntity();
        turma.setNome(request.nome());
        turma.setAlunos(request.alunos());
        Optional<DocenteEntity> docente = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (docente.isPresent()){
            docenteValido = docente.get();
        }
        turma.setProfessor(docenteValido);
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }
        turma.setId_curso(cursoValido);
        service.salvar(turma);

        return ResponseEntity.created(null).body(new TurmaResponse(turma.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaEntity> listarPorId(@PathVariable Long id){
        TurmaEntity turma = service.listarPorId(id);
        return ResponseEntity.ok(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaEntity> atualizar(@PathVariable Long id, @RequestBody TurmaRequest request){
        TurmaEntity turma = service.listarPorId(id);
        Optional<DocenteEntity> docente = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (docente.isPresent()){
            docenteValido = docente.get();
        }
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }
        turma.setId_curso(cursoValido);
        turma.setProfessor(docenteValido);
        turma.setAlunos(request.alunos());
        turma.setNome(request.nome());
        service.atualizar(turma.getId());
        return ResponseEntity.ok(turma);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TurmaEntity>> listarTodos(){
        List<TurmaEntity> listaTurmas = service.listarTodos();
        if (listaTurmas.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaTurmas);
    }

    @GetMapping("/aluno/{id}/pontuacao")
    public ResponseEntity<Double> listarTodos(@PathVariable Long id){
        List<NotaEntity> listaNotas = notaService.listarPorIdAluno(id);
        if (alunoService.listarPorId(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        int numeroDeMaterias = alunoService.listarPorId(id).getId_turma().getId_curso().getMaterias().size();
        double pontuacao = 0;
        for (NotaEntity notas: listaNotas){
            pontuacao += notas.getValor();
        }
        return ResponseEntity.ok(pontuacao/numeroDeMaterias);
    }
}

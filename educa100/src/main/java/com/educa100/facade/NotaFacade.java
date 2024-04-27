package com.educa100.facade;

import com.educa100.controller.dto.request.NotaRequest;
import com.educa100.datasource.entity.*;
import com.educa100.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notas")
@Slf4j
public class NotaFacade {

    private final NotaServiceImpl service;

    private final AlunoServiceImpl alunoService;

    private final DocenteServiceImpl docenteService;

    private final MateriaServiceImpl materiaService;

    private final UsuarioServiceImpl usuarioService;

    public NotaFacade(NotaServiceImpl service, AlunoServiceImpl alunoService, DocenteServiceImpl docenteService, MateriaServiceImpl materiaService, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.alunoService = alunoService;
        this.docenteService = docenteService;
        this.materiaService = materiaService;
        this.usuarioService = usuarioService;
    }


    public NotaEntity criarNotas(NotaRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5 || usuarioLogado.getId_papel().getId() == 3 || usuarioLogado.getId_papel().getId() == 2){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou professores podem criar notas");
        }

        NotaEntity nota = new NotaEntity();

        Optional<AlunoEntity> aluno = Optional.ofNullable(alunoService.listarPorId(request.id_aluno()));
        AlunoEntity alunoValido = null;
        if (aluno.isPresent()){
            alunoValido = aluno.get();
        }else{
            log.error("Aluno é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Aluno é nullo");
        }
        nota.setId_aluno(alunoValido);

        Optional<DocenteEntity> professor = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (professor.isPresent()){
            docenteValido = professor.get();
        }else{
            log.error("Docente é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Docente é nullo");
        }
        nota.setId_professor(docenteValido);

        Optional<MateriaEntity> materia = Optional.ofNullable(materiaService.listarPorId(request.id_materia()));
        MateriaEntity materiaValido = null;
        if (materia.isPresent()){
            materiaValido = materia.get();
        }else{
            log.error("Materia é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Materia é nullo");
        }
        nota.setId_materia(materiaValido);
        if (request.data() == null){
            nota.setData(LocalDate.now());
        } else {
            nota.setData(request.data());
        }
        nota.setValor(request.valor());
        service.salvar(nota);

        return nota;
    }

    public NotaEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        NotaEntity nota = service.listarPorId(id);
        if (usuarioLogado.getId_papel().getId() != 1  && !nota.getId_aluno().getId_usuario().getId().equals(usuarioLogado.getId())  && usuarioLogado.getId_papel().getId() != 4 && usuarioLogado.getId_papel().getId() != 2){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores/o próprio alunos/professores ou pedagogos podem listar nota");
        }
        return nota;
    }

    public NotaEntity atualizar(Long id,NotaRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1 && usuarioLogado.getId_papel().getId() != 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou o professores atualizar notas");
        }

        NotaEntity nota = service.listarPorId(id);

        Optional<AlunoEntity> aluno = Optional.ofNullable(alunoService.listarPorId(request.id_aluno()));
        AlunoEntity alunoValido = null;
        if (aluno.isPresent()){
            alunoValido = aluno.get();
        }else{
            log.error("Aluno é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Aluno é nullo");
        }
        nota.setId_aluno(alunoValido);


        Optional<DocenteEntity> professor = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (professor.isPresent()){
            docenteValido = professor.get();
        }else{
            log.error("Docente é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Docente é nullo");
        }
        nota.setId_professor(docenteValido);

        Optional<MateriaEntity> materia = Optional.ofNullable(materiaService.listarPorId(request.id_materia()));
        MateriaEntity materiaValido = null;
        if (materia.isPresent()){
            materiaValido = materia.get();
        }else{
            log.error("Materia é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Materia é nullo");
        }
        nota.setId_materia(materiaValido);

        if (request.data() == null){
            nota.setData(nota.getData());
        } else {
            nota.setData(request.data());
        }
        nota.setValor(request.valor());

        service.atualizar(nota.getId());
        return nota;
    }

    public void deletar(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1 ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar alunos");
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<NotaEntity> listarTodos(JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1  && usuarioLogado.getId_papel().getId() != 4 && usuarioLogado.getId_papel().getId() != 2){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores/professores ou pedagogos podem listar todas as notas");
        }
        List<NotaEntity> listaNotas = service.listarTodos();
        if (listaNotas.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return listaNotas;
    }
}

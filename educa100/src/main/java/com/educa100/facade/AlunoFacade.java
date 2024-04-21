package com.educa100.facade;

import com.educa100.controller.dto.request.AlunoRequest;
import com.educa100.controller.dto.response.AlunoResponse;
import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.entity.NotaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.service.AlunoServiceImpl;
import com.educa100.service.NotaServiceImpl;
import com.educa100.service.TurmaServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public final class AlunoFacade {
    private final AlunoServiceImpl service;

    private final TurmaServiceImpl turmaService;

    private final UsuarioServiceImpl usuarioService;

    private final NotaServiceImpl notaService;

    public AlunoFacade(AlunoServiceImpl service, TurmaServiceImpl turmaService, UsuarioServiceImpl usuarioService, NotaServiceImpl notaService) {
        this.service = service;
        this.turmaService = turmaService;
        this.usuarioService = usuarioService;
        this.notaService = notaService;
    }
    public AlunoEntity criarAluno(AlunoRequest request, JwtAuthenticationToken jwt) throws Exception {
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1 || usuarioLogado.getId_papel().getId() != 5){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou alunos podem criar alunos");
        }
        AlunoEntity aluno = new AlunoEntity();
        boolean nomeEmUso = false;
        List<AlunoEntity> Alunos = service.listarTodos();
        for (AlunoEntity a : Alunos){
            if (a.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            aluno.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        aluno.setData_nascimento(request.dataNascimento());

        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        } else{
            log.error("Usuario é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (usuarioLogado.getId_papel().getId() != 1  && !usuarioValido.getId_papel().equals(usuarioLogado.getId_papel())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO,você so pode cadastrar você mesmo");
        }
        aluno.setId_usuario(usuarioValido);
        Optional<TurmaEntity> turma = Optional.ofNullable(turmaService.listarPorId(request.id_turma()));
        TurmaEntity turmaValida = null;
        if (turma.isPresent()){
            turmaValida = turma.get();
        } else {
            log.error("Turma é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        aluno.setId_turma(turmaValida);
        service.salvar(aluno);
        return aluno;
    }

    public AlunoEntity listarPorId(Long id,JwtAuthenticationToken jwt) throws Exception {
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1  || usuarioLogado.getId_papel().getId() != 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou alunos podem listar alunos");
        }
        AlunoEntity aluno = service.listarPorId(id);
        if (usuarioLogado.getId_papel().getId() != 1  || aluno.getId_usuario().getId().equals((usuarioLogado.getId()))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou o próprio alunos podem se listar");
        }
        return aluno;
    }

    public AlunoEntity atualizar(Long id, AlunoRequest request,JwtAuthenticationToken jwt) throws Exception {
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1  || usuarioLogado.getId_papel().getId() != 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"ACESSO NEGADO, só adiministradores ou alunos podem atualizar alunos");
        }
        AlunoEntity aluno = service.listarPorId(id);
        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        }
        if (usuarioLogado.getId_papel().getId() != 1  && !usuarioValido.getId_papel().equals(usuarioLogado.getId_papel())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, você so pode atualizar você mesmo");
        }
        Optional<TurmaEntity> turma = Optional.ofNullable(turmaService.listarPorId(request.id_turma()));
        TurmaEntity turmaValida = null;
        if (turma.isPresent()){
            turmaValida = turma.get();
        }
        boolean nomeEmUso = false;
        List<AlunoEntity> Alunos = service.listarTodos();
        for (AlunoEntity a : Alunos){
            if (a.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            aluno.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é invalido");
        }
        aluno.setData_nascimento(request.dataNascimento());
        if (!request.id_usuario().equals(usuarioValido.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Você não pode modificar o id de usuario de um aluno já cadastrado");
        }
        aluno.setId_usuario(usuarioValido);
        aluno.setId_turma(turmaValida);
        service.atualizar(aluno.getId());
        return aluno;
    }

    public void deletar(Long id, JwtAuthenticationToken jwt) throws Exception {
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1 ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar alunos");
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<AlunoEntity> listarTodos(JwtAuthenticationToken jwt) throws Exception {
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 1 ) {
            List<AlunoEntity> listaAlunos = service.listarTodos();
            if (listaAlunos.isEmpty()) {
                log.info("Não ha alunos para serem listados");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return listaAlunos;
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"ACESSO NEGADO, so administradores podem listar todos os alunos");
        }
    }
    public Double getPontuacao(Long id,JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        AlunoEntity aluno = service.listarPorId(id);
        List<NotaEntity> listaNotas = notaService.listarPorIdAluno(id);
        if (service.listarPorId(id) == null || usuarioLogado.getId().equals(aluno.getId_usuario().getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        int numeroDeMaterias = service.listarPorId(id).getId_turma().getId_curso().getMaterias().size();
        double pontuacao = 0;
        for (NotaEntity notas: listaNotas){
            pontuacao += notas.getValor();
        }
        return ((pontuacao/numeroDeMaterias)*10);
    }

    public List<NotaEntity> listaAlunoId(Long id_aluno,JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        List<NotaEntity> listaNotas = notaService.listarPorIdAluno(id_aluno);
        AlunoEntity aluno = service.listarPorId(id_aluno);
        if (listaNotas.isEmpty() || usuarioLogado.getId().equals(aluno.getId_usuario().getId()) ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return listaNotas;
    }
}

package com.educa100.facade;

import com.educa100.controller.dto.request.MateriaRequest;
import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.service.CursoServiceImpl;
import com.educa100.service.MateriaServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MateriaFacade {

    private final MateriaServiceImpl service;

    private final CursoServiceImpl cursoService;
    private final UsuarioServiceImpl usuarioService;


    public MateriaFacade(MateriaServiceImpl service, CursoServiceImpl cursoService, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.cursoService = cursoService;
        this.usuarioService = usuarioService;
    }


    public MateriaEntity criarMateria(MateriaRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores criar Materias");
        }
        MateriaEntity materia = new MateriaEntity();
        if (!request.nome().isBlank()){
            materia.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é invalido");
        }
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }else{
            log.error("Curso é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso é nullo");
        }
        materia.setId_curso(cursoValido);
        service.salvar(materia);

        return materia;
    }

    public MateriaEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        MateriaEntity materia = service.listarPorId(id);
        return materia;
    }

    public MateriaEntity atualizar(Long id, MateriaRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores atualizar Materias");
        }

        MateriaEntity materia = service.listarPorId(id);

        if (!request.nome().isBlank()){
            materia.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome é invalido");
        }

        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }else{
            log.error("Curso é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso é nullo");
        }
        materia.setId_curso(cursoValido);
        service.atualizar(materia.getId());
        return materia;
    }

    public void deletar(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId()  != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar materias");
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<MateriaEntity> listarTodos(JwtAuthenticationToken jwt){
        List<MateriaEntity> listaMaterias = service.listarTodos();
        if (listaMaterias.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não a Materias para serem listadas");
        }
        return listaMaterias;
    }

    public  List<MateriaEntity> listaCursoId(Long id, JwtAuthenticationToken jwt){
        List<MateriaEntity> listaMaterias = service.listarPorIdCurso(id);
        if (listaMaterias.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não a Materias em Curso com id:" + id +" para serem listadas");
        }
        return listaMaterias;
    }
}

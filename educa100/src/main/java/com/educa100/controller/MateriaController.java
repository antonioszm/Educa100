package com.educa100.controller;

import com.educa100.controller.dto.request.CursoRequest;
import com.educa100.controller.dto.request.MateriaRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.AlunoResponse;
import com.educa100.controller.dto.response.CursoResponse;
import com.educa100.controller.dto.response.MateriaResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.MateriaFacade;
import com.educa100.service.CursoServiceImpl;
import com.educa100.service.MateriaService;
import com.educa100.service.MateriaServiceImpl;
import com.educa100.service.TurmaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materias")
@Slf4j
public class MateriaController {

    private final MateriaFacade facade;

    public MateriaController(MateriaFacade facade) {
        this.facade = facade;
    }


    @PostMapping
    public ResponseEntity<MateriaResponse> criarMateria(@RequestBody MateriaRequest request, JwtAuthenticationToken jwt){
        MateriaEntity materia = facade.criarMateria(request, jwt);
        log.info("Materia cirado com sucesso!");
        return ResponseEntity.created(null).body(new MateriaResponse(materia.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaEntity> listarPorId(@PathVariable Long id,JwtAuthenticationToken jwt){
        log.info("Materia com {id} foi listado" + id);
        MateriaEntity materia = facade.listarPorId(id, jwt);

        return ResponseEntity.ok(materia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaEntity> atualizar(@PathVariable Long id, @RequestBody MateriaRequest request,JwtAuthenticationToken jwt){
        MateriaEntity materia = facade.atualizar(id, request, jwt);
        log.info("Materia atualizado com sucesso!");
        return ResponseEntity.ok(materia);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id,JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Materia deletada com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<MateriaEntity>> listarTodos(JwtAuthenticationToken jwt){
        List<MateriaEntity> listaDeMateria = facade.listarTodos(jwt);
        log.info("Todas as Materias listadas com sucesso!");
        return ResponseEntity.ok(listaDeMateria);
    }
}

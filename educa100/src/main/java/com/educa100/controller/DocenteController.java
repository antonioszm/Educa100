package com.educa100.controller;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.controller.dto.response.AlunoResponse;
import com.educa100.controller.dto.response.DocenteResponse;
import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.facade.DocenteFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/docentes")
@Slf4j
public class DocenteController {

    private final DocenteFacade facade;

    public DocenteController(DocenteFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<DocenteResponse> criarDocente(@RequestBody DocenteRequest request, JwtAuthenticationToken jwt){
        DocenteEntity docente = facade.criarDocente(request, jwt);
        log.info("Docente cirado com sucesso!");
        return ResponseEntity.created(null).body(new DocenteResponse(docente.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteEntity> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt){
        log.info("Docente com {id} foi listado" + id);
        DocenteEntity docente = facade.listarPorId(id, jwt);
        return ResponseEntity.ok(docente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteEntity> atualizar(@PathVariable Long id,@RequestBody DocenteRequest request,JwtAuthenticationToken jwt){
        DocenteEntity docente = facade.atualizar(id, request, jwt);
        log.info("Docente atualizado com sucesso!");
        return ResponseEntity.ok(docente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id, JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Docente deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<DocenteEntity>> listarTodos(JwtAuthenticationToken jwt){
        List<DocenteEntity> ListaDeDocentes = facade.listarTodos(jwt);
        log.info("Todos os docentes listados com sucesso!");
        return ResponseEntity.ok(ListaDeDocentes);
    }
}

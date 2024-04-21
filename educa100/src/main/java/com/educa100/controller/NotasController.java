package com.educa100.controller;

import com.educa100.controller.dto.request.MateriaRequest;
import com.educa100.controller.dto.request.NotaRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.AlunoResponse;
import com.educa100.controller.dto.response.MateriaResponse;
import com.educa100.controller.dto.response.NotaResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.NotaFacade;
import com.educa100.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notas")
@Slf4j
public class NotasController {

    public final NotaFacade facade;

    public NotasController(NotaFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<NotaResponse> criarNotas(@RequestBody NotaRequest request, JwtAuthenticationToken jwt){
        NotaEntity nota = facade.criarNotas(request, jwt);
        log.info("Nota cirado com sucesso!");
        return ResponseEntity.created(null).body(new NotaResponse(nota.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaEntity> listarPorId(@PathVariable Long id,JwtAuthenticationToken jwt){
        log.info("Nota com {id} foi listado" + id);
        NotaEntity nota = facade.listarPorId(id, jwt);

        return ResponseEntity.ok(nota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaEntity> atualizar(@PathVariable Long id, @RequestBody NotaRequest request,JwtAuthenticationToken jwt){
        NotaEntity nota = facade.atualizar(id, request, jwt);
        log.info("Nota atualizado com sucesso!");
        return ResponseEntity.ok(nota);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id, JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Nota deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<NotaEntity>> listarTodos(JwtAuthenticationToken jwt){
        List<NotaEntity> listaDeNotas = facade.listarTodos(jwt);
        log.info("Todas as notas listadas com sucesso!");
        return ResponseEntity.ok(listaDeNotas);
    }
}

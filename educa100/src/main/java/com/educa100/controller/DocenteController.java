package com.educa100.controller;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.controller.dto.response.DocenteResponse;
import com.educa100.controller.dto.response.UsuarioResponse;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.service.DocenteServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/docentes")
public class DocenteController {

    private final DocenteServiceImpl service;
    private final UsuarioServiceImpl usuarioService;
    public DocenteController(DocenteServiceImpl service, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<DocenteResponse> criarDocente(@RequestBody DocenteRequest request){

        DocenteEntity docente = new DocenteEntity();
        docente.setNome(request.nome());
        docente.setData_entrada(request.dataEntrada());
        Optional<UsuarioEntity> usuario = usuarioService.listarPorId(request.id_usuario());
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        }
        docente.setId_usuario(usuarioValido);

        service.salvar(docente);

        return ResponseEntity.created(null).body(new DocenteResponse(docente.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteEntity> listarPorId(@PathVariable Long id){
        DocenteEntity docente = service.listarPorId(id);
        return ResponseEntity.ok(docente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteEntity> atualizar(@PathVariable Long id,@RequestBody DocenteRequest request){
        DocenteEntity docente = service.listarPorId(id);
        Optional<UsuarioEntity> usuario = usuarioService.listarPorId(request.id_usuario());
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        }
        docente.setId_usuario(usuarioValido);
        docente.setNome(request.nome());
        docente.setData_entrada(request.dataEntrada());
        service.atualizar(docente.getId());
        return ResponseEntity.ok(docente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<DocenteEntity>> listarTodos(){
        List<DocenteEntity> listaDocentes = service.listarTodos();
        if (listaDocentes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaDocentes);
    }
}

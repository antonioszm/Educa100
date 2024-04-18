package com.educa100.controller;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.controller.dto.response.DocenteResponse;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.service.DocenteServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    private final DocenteServiceImpl service;
    public DocenteController(DocenteServiceImpl service, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<DocenteResponse> criarDocente(@RequestBody DocenteRequest request){

        DocenteEntity docente = new DocenteEntity();
        docente.setNome(request.nome());
        docente.setData_entrada(request.dataEntrada());
        docente.setId_usuario(request.id_usuario());

        service.salvar();
    }
}

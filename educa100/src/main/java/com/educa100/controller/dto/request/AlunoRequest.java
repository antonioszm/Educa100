package com.educa100.controller.dto.request;

import com.educa100.datasource.entity.PapelEntity;

import java.time.LocalDate;
import java.util.Date;

public record AlunoRequest(String nome, LocalDate dataNascimento, Long id_usuario, Long id_papel) {
}

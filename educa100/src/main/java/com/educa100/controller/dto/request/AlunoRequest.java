package com.educa100.controller.dto.request;

import com.educa100.datasource.entity.PapelEntity;

import java.util.Date;

public record AlunoRequest(String nome, Date dataNascimento, int id_usuario, int id_papel) {
}
package com.educa100.controller.dto.request;

import java.util.Date;

public record DocenteRequest(String nome, Date dataEntrada, int id_usuario) {
}

package com.educa100.controller.dto.request;

import java.time.LocalDate;
import java.util.Date;

public record DocenteRequest(String nome, LocalDate dataEntrada, Long id_usuario) {
}

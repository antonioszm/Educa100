package com.educa100.controller.dto.response;

import java.time.LocalDate;

public record DocenteResponse(Long id, String nome, LocalDate dataEntrada, Long usuario, Long idPapel) {
}

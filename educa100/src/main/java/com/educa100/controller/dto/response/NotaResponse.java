package com.educa100.controller.dto.response;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record NotaResponse(Long id, Long aluno, Long professor, Long materia, double valor, LocalDate data) {
}

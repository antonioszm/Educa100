package com.educa100.controller.dto.request;

import java.time.LocalDate;
import java.util.Date;

public record NotaRequest(Long id_aluno, Long id_professor, Long id_materia, double valor, LocalDate data) {
}

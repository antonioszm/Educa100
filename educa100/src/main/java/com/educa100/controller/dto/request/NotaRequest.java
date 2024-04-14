package com.educa100.controller.dto.request;

import java.util.Date;

public record NotaRequest(int id_aluno,int id_professor,int id_materia,double valor,Date data) {
}

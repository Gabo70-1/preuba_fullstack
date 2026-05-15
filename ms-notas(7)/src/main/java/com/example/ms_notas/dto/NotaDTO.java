package com.example.ms_notas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NotaDTO {

    @NotNull(message = "La nota es obligatoria")
    private Double nota;

    @NotNull(message = "El estudiante es obligatorio")
    private Long idEstudiante;

    @NotNull(message = "La evaluación es obligatoria")
    private Long idEvaluacion;
}
package com.example.ms_evaluaciones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class EvaluacionDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreE;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotNull(message = "La ponderación es obligatoria")
    private Double ponderacion;

    @NotNull(message = "El curso es obligatorio")
    private Long idCurso;
}
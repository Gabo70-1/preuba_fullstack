package com.example.ms_historial_academico.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HistorialAcademicoDTO {

    @NotNull(message = "El promedio final es obligatorio")
    private Double promedioFinal;

    @NotNull(message = "La asistencia final es obligatoria")
    private Double asistenciaFinal;

    @NotNull(message = "El estudiante es obligatorio")
    private Long idEstudiante;

    @NotNull(message = "El curso es obligatorio")
    private Long idCurso;
}
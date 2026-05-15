package com.example.ms_inscripciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InscripcionDTO {

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotNull(message = "El estudiante es obligatorio")
    private Long idEstudiante;

    @NotNull(message = "El curso es obligatorio")
    private Long idCurso;
}

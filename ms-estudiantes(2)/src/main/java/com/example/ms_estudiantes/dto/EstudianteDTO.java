package com.example.ms_estudiantes.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EstudianteDTO {

    @NotBlank(message = "El rut es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La carrera es obligatoria")
    private String carrera;

    @NotNull(message = "La facultad es obligatoria")
    private Long idFacultad;
}
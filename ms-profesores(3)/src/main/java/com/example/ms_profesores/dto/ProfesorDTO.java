package com.example.ms_profesores.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProfesorDTO {
    @NotBlank(message = "El rut es obligatorio")
    private String rutP;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreP;

    @NotBlank(message = "El email es obligatorio")
    private String emailP;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotNull(message = "La facultad es obligatoria")
    private Long idFacultad;
}

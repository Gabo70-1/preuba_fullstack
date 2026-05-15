package com.example.ms_cursos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class CursoDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreC;

    @NotNull(message = "La cantidad de estudiantes es obligatoria")
    private Integer cantidadE;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El profesor es obligatorio")
    private Long idProfesor;

    @NotNull(message = "La facultad es obligatoria")
    private Long idFacultad;
}
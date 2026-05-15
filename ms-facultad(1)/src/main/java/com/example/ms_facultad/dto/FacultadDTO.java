package com.example.ms_facultad.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FacultadDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;
}
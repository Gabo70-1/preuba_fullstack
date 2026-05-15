package com.example.ms_inscripciones.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionResponse {

    private Long id;

    private String fecha;

    private EstudianteResponse estudiante;

    private CursoResponse curso;
}
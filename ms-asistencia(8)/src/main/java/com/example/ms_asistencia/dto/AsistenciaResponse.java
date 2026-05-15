package com.example.ms_asistencia.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaResponse {

    private Long id;
    private String fechaClase;
    private Boolean asistencia;

    private EstudianteResponse estudiante;
    private CursoResponse curso;
}
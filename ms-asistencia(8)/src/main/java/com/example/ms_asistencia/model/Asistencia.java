package com.example.ms_asistencia.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asistencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaClase;

    private Boolean asistencia;

    private Long idEstudiante;

    private Long idCurso;
}
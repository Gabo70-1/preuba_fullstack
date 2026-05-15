package com.example.ms_evaluaciones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evaluaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreE;

    private String fecha;

    private Double ponderacion;

    private Long idCurso;
}
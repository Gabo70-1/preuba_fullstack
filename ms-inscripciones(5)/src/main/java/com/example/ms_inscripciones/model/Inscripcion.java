package com.example.ms_inscripciones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;

    private Long idEstudiante;
    private Long idCurso;
}
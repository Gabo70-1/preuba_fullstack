package com.example.ms_inscripciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_inscripciones.model.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}
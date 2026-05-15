package com.example.ms_asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_asistencia.model.Asistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
}
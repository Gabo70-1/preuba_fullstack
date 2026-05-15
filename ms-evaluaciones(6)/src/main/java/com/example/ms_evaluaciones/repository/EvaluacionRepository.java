package com.example.ms_evaluaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_evaluaciones.model.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
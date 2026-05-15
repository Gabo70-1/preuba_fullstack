package com.example.ms_historial_academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_historial_academico.model.HistorialAcademico;

public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademico, Long> {
}
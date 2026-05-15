package com.example.ms_cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_cursos.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
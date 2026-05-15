package com.example.ms_horario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_horario.model.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
}
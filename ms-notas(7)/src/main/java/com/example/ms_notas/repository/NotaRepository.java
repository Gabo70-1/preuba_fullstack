package com.example.ms_notas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_notas.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
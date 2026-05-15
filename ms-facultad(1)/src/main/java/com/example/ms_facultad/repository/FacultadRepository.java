package com.example.ms_facultad.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_facultad.model.Facultad;

@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long> {
}
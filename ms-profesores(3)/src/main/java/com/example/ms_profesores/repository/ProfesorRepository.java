package com.example.ms_profesores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_profesores.model.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long>{

}

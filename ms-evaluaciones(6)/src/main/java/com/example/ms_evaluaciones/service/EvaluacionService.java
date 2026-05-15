package com.example.ms_evaluaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_evaluaciones.client.CursoClient;
import com.example.ms_evaluaciones.dto.EvaluacionDTO;
import com.example.ms_evaluaciones.dto.EvaluacionResponse;
import com.example.ms_evaluaciones.model.Evaluacion;
import com.example.ms_evaluaciones.repository.EvaluacionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluacionService {

    private final EvaluacionRepository repo;
    private final CursoClient cursoClient;

    public EvaluacionResponse crear(EvaluacionDTO dto, String token) {

        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        Evaluacion evaluacion = repo.save(
                new Evaluacion(
                        null,
                        dto.getNombreE(),
                        dto.getFecha(),
                        dto.getPonderacion(),
                        dto.getIdCurso()
                )
        );

        return mapToResponse(evaluacion, token);
    }

    public List<EvaluacionResponse> listar(String token) {

        return repo.findAll()
                .stream()
                .map(e -> mapToResponse(e, token))
                .toList();
    }

    public EvaluacionResponse obtener(Long id, String token) {

        Evaluacion evaluacion = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evaluación no encontrada"));

        return mapToResponse(evaluacion, token);
    }

    public EvaluacionResponse actualizar(Long id, EvaluacionDTO dto, String token) {

        Evaluacion e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evaluación no encontrada"));

        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        e.setNombreE(dto.getNombreE());
        e.setFecha(dto.getFecha());
        e.setPonderacion(dto.getPonderacion());
        e.setIdCurso(dto.getIdCurso());

        return mapToResponse(repo.save(e), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private EvaluacionResponse mapToResponse(Evaluacion evaluacion, String token) {

        var curso = cursoClient.obtenerCurso(evaluacion.getIdCurso(), token);

        return EvaluacionResponse.builder()
                .id(evaluacion.getId())
                .nombreE(evaluacion.getNombreE())
                .fecha(evaluacion.getFecha())
                .ponderacion(evaluacion.getPonderacion())
                .curso(curso)
                .build();
    }
}
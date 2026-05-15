package com.example.ms_notas.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_notas.client.EstudianteClient;
import com.example.ms_notas.client.EvaluacionClient;
import com.example.ms_notas.dto.NotaDTO;
import com.example.ms_notas.dto.NotaResponse;
import com.example.ms_notas.model.Nota;
import com.example.ms_notas.repository.NotaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotaService {

    private final NotaRepository repo;
    private final EstudianteClient estudianteClient;
    private final EvaluacionClient evaluacionClient;

    public NotaResponse crear(NotaDTO dto, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var evaluacion = evaluacionClient.obtenerEvaluacion(dto.getIdEvaluacion(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (evaluacion == null) {
            throw new RuntimeException("Evaluación no existe");
        }

        Nota nota = repo.save(
                new Nota(
                        null,
                        dto.getNota(),
                        dto.getIdEstudiante(),
                        dto.getIdEvaluacion()
                )
        );

        return mapToResponse(nota, token);
    }

    public List<NotaResponse> listar(String token) {

        return repo.findAll()
                .stream()
                .map(n -> mapToResponse(n, token))
                .toList();
    }

    public NotaResponse obtener(Long id, String token) {

        Nota nota = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada"));

        return mapToResponse(nota, token);
    }

    public NotaResponse actualizar(Long id, NotaDTO dto, String token) {

        Nota n = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada"));

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var evaluacion = evaluacionClient.obtenerEvaluacion(dto.getIdEvaluacion(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (evaluacion == null) {
            throw new RuntimeException("Evaluación no existe");
        }

        n.setNota(dto.getNota());
        n.setIdEstudiante(dto.getIdEstudiante());
        n.setIdEvaluacion(dto.getIdEvaluacion());

        return mapToResponse(repo.save(n), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private NotaResponse mapToResponse(Nota nota, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(nota.getIdEstudiante(), token);
        var evaluacion = evaluacionClient.obtenerEvaluacion(nota.getIdEvaluacion(), token);

        return NotaResponse.builder()
                .id(nota.getId())
                .nota(nota.getNota())
                .estudiante(estudiante)
                .evaluacion(evaluacion)
                .build();
    }
}
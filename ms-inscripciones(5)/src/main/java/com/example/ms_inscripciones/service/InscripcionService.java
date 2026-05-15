package com.example.ms_inscripciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_inscripciones.client.CursoClient;
import com.example.ms_inscripciones.client.EstudianteClient;
import com.example.ms_inscripciones.dto.InscripcionDTO;
import com.example.ms_inscripciones.dto.InscripcionResponse;
import com.example.ms_inscripciones.model.Inscripcion;
import com.example.ms_inscripciones.repository.InscripcionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InscripcionService {

    private final InscripcionRepository repo;
    private final EstudianteClient estudianteClient;
    private final CursoClient cursoClient;

    public InscripcionResponse crear(InscripcionDTO dto, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        Inscripcion inscripcion = repo.save(
                new Inscripcion(
                        null,
                        dto.getFecha(),
                        dto.getIdEstudiante(),
                        dto.getIdCurso()
                )
        );

        return mapToResponse(inscripcion, token);
    }

    public List<InscripcionResponse> listar(String token) {
        return repo.findAll()
                .stream()
                .map(i -> mapToResponse(i, token))
                .toList();
    }

    public InscripcionResponse obtener(Long id, String token) {
        Inscripcion inscripcion = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscripción no encontrada"));

        return mapToResponse(inscripcion, token);
    }

    public InscripcionResponse actualizar(Long id, InscripcionDTO dto, String token) {
        Inscripcion i = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscripción no encontrada"));

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        i.setFecha(dto.getFecha());
        i.setIdEstudiante(dto.getIdEstudiante());
        i.setIdCurso(dto.getIdCurso());

        return mapToResponse(repo.save(i), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private InscripcionResponse mapToResponse(Inscripcion inscripcion, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(inscripcion.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(inscripcion.getIdCurso(), token);

        return InscripcionResponse.builder()
                .id(inscripcion.getId())
                .fecha(inscripcion.getFecha())
                .estudiante(estudiante)
                .curso(curso)
                .build();
    }
}
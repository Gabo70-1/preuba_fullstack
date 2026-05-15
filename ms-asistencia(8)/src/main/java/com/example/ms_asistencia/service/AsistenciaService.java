package com.example.ms_asistencia.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_asistencia.client.CursoClient;
import com.example.ms_asistencia.client.EstudianteClient;
import com.example.ms_asistencia.dto.AsistenciaDTO;
import com.example.ms_asistencia.dto.AsistenciaResponse;
import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.repository.AsistenciaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsistenciaService {

    private final AsistenciaRepository repo;
    private final EstudianteClient estudianteClient;
    private final CursoClient cursoClient;

    public AsistenciaResponse crear(AsistenciaDTO dto, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        Asistencia asistencia = repo.save(
                new Asistencia(
                        null,
                        dto.getFechaClase(),
                        dto.getAsistencia(),
                        dto.getIdEstudiante(),
                        dto.getIdCurso()
                )
        );

        return mapToResponse(asistencia, token);
    }

    public List<AsistenciaResponse> listar(String token) {
        return repo.findAll()
                .stream()
                .map(a -> mapToResponse(a, token))
                .toList();
    }

    public AsistenciaResponse obtener(Long id, String token) {
        Asistencia asistencia = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asistencia no encontrada"));

        return mapToResponse(asistencia, token);
    }

    public AsistenciaResponse actualizar(Long id, AsistenciaDTO dto, String token) {
        Asistencia a = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asistencia no encontrada"));

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        a.setFechaClase(dto.getFechaClase());
        a.setAsistencia(dto.getAsistencia());
        a.setIdEstudiante(dto.getIdEstudiante());
        a.setIdCurso(dto.getIdCurso());

        return mapToResponse(repo.save(a), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private AsistenciaResponse mapToResponse(Asistencia asistencia, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(asistencia.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(asistencia.getIdCurso(), token);

        return AsistenciaResponse.builder()
                .id(asistencia.getId())
                .fechaClase(asistencia.getFechaClase())
                .asistencia(asistencia.getAsistencia())
                .estudiante(estudiante)
                .curso(curso)
                .build();
    }
}
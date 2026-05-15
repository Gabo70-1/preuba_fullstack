package com.example.ms_historial_academico.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_historial_academico.client.CursoClient;
import com.example.ms_historial_academico.client.EstudianteClient;
import com.example.ms_historial_academico.dto.HistorialAcademicoDTO;
import com.example.ms_historial_academico.dto.HistorialAcademicoResponse;
import com.example.ms_historial_academico.model.HistorialAcademico;
import com.example.ms_historial_academico.repository.HistorialAcademicoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistorialAcademicoService {

    private final HistorialAcademicoRepository repo;
    private final EstudianteClient estudianteClient;
    private final CursoClient cursoClient;

    public HistorialAcademicoResponse crear(HistorialAcademicoDTO dto, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        String estado = calcularEstado(dto.getPromedioFinal(), dto.getAsistenciaFinal());

        HistorialAcademico historial = repo.save(
                new HistorialAcademico(
                        null,
                        dto.getPromedioFinal(),
                        dto.getAsistenciaFinal(),
                        estado,
                        dto.getIdEstudiante(),
                        dto.getIdCurso()
                )
        );

        return mapToResponse(historial, token);
    }

    public List<HistorialAcademicoResponse> listar(String token) {
        return repo.findAll()
                .stream()
                .map(h -> mapToResponse(h, token))
                .toList();
    }

    public HistorialAcademicoResponse obtener(Long id, String token) {
        HistorialAcademico historial = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));

        return mapToResponse(historial, token);
    }

    public HistorialAcademicoResponse actualizar(Long id, HistorialAcademicoDTO dto, String token) {

        HistorialAcademico h = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));

        var estudiante = estudianteClient.obtenerEstudiante(dto.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (estudiante == null) {
            throw new RuntimeException("Estudiante no existe");
        }

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        h.setPromedioFinal(dto.getPromedioFinal());
        h.setAsistenciaFinal(dto.getAsistenciaFinal());
        h.setEstado(calcularEstado(dto.getPromedioFinal(), dto.getAsistenciaFinal()));
        h.setIdEstudiante(dto.getIdEstudiante());
        h.setIdCurso(dto.getIdCurso());

        return mapToResponse(repo.save(h), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private String calcularEstado(Double promedio, Double asistencia) {
        if (promedio >= 4.0 && asistencia >= 75.0) {
            return "Aprobado";
        }
        return "Reprobado";
    }

    private HistorialAcademicoResponse mapToResponse(HistorialAcademico historial, String token) {

        var estudiante = estudianteClient.obtenerEstudiante(historial.getIdEstudiante(), token);
        var curso = cursoClient.obtenerCurso(historial.getIdCurso(), token);

        return HistorialAcademicoResponse.builder()
                .id(historial.getId())
                .promedioFinal(historial.getPromedioFinal())
                .asistenciaFinal(historial.getAsistenciaFinal())
                .estado(historial.getEstado())
                .estudiante(estudiante)
                .curso(curso)
                .build();
    }
}
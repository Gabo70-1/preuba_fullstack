package com.example.ms_cursos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_cursos.client.FacultadClient;
import com.example.ms_cursos.client.ProfesorClient;
import com.example.ms_cursos.dto.CursoDTO;
import com.example.ms_cursos.dto.CursoResponse;
import com.example.ms_cursos.model.Curso;
import com.example.ms_cursos.repository.CursoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursoService {

    private final CursoRepository repo;
    private final ProfesorClient profesorClient;
    private final FacultadClient facultadClient;

    public CursoResponse crear(CursoDTO dto, String token) {

        var profesor = profesorClient.obtenerProfesor(dto.getIdProfesor(), token);
        var facultad = facultadClient.obtenerFacultad(dto.getIdFacultad(), token);

        if (profesor == null) {
            throw new RuntimeException("Profesor no existe");
        }

        if (facultad == null) {
            throw new RuntimeException("Facultad no existe");
        }

        Curso curso = repo.save(
                new Curso(
                        null,
                        dto.getNombreC(),
                        dto.getCantidadE(),
                        dto.getDescripcion(),
                        dto.getIdProfesor(),
                        dto.getIdFacultad()
                )
        );

        return mapToResponse(curso, token);
    }

    public List<CursoResponse> listar(String token) {

        return repo.findAll()
                .stream()
                .map(c -> mapToResponse(c, token))
                .toList();
    }

    public CursoResponse obtener(Long id, String token) {

        Curso curso = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        return mapToResponse(curso, token);
    }

    public CursoResponse actualizar(Long id, CursoDTO dto, String token) {

        Curso c = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        var profesor = profesorClient.obtenerProfesor(dto.getIdProfesor(), token);
        var facultad = facultadClient.obtenerFacultad(dto.getIdFacultad(), token);

        if (profesor == null) {
            throw new RuntimeException("Profesor no existe");
        }

        if (facultad == null) {
            throw new RuntimeException("Facultad no existe");
        }

        c.setNombreC(dto.getNombreC());
        c.setCantidadE(dto.getCantidadE());
        c.setDescripcion(dto.getDescripcion());
        c.setIdProfesor(dto.getIdProfesor());
        c.setIdFacultad(dto.getIdFacultad());

        return mapToResponse(repo.save(c), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private CursoResponse mapToResponse(Curso curso, String token) {

        var profesor = profesorClient.obtenerProfesor(curso.getIdProfesor(), token);
        var facultad = facultadClient.obtenerFacultad(curso.getIdFacultad(), token);

        return CursoResponse.builder()
                .id(curso.getId())
                .nombreC(curso.getNombreC())
                .cantidadE(curso.getCantidadE())
                .descripcion(curso.getDescripcion())
                .profesor(profesor)
                .facultad(facultad)
                .build();
    }
}
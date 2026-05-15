package com.example.ms_estudiantes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_estudiantes.client.FacultadClient;
import com.example.ms_estudiantes.dto.EstudianteDTO;
import com.example.ms_estudiantes.dto.EstudianteResponse;
import com.example.ms_estudiantes.model.Estudiante;
import com.example.ms_estudiantes.repository.EstudianteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstudianteService {

    private final EstudianteRepository repo;
    private final FacultadClient facultadClient;

    public EstudianteResponse crear(EstudianteDTO dto, String token) {

        var facultad = facultadClient.obtenerFacultad(dto.getIdFacultad(), token);

        if (facultad == null) {
            throw new RuntimeException("Facultad no existe");
        }

        Estudiante estudiante = repo.save(
                new Estudiante(
                        null,
                        dto.getRut(),
                        dto.getNombre(),
                        dto.getEmail(),
                        dto.getCarrera(),
                        dto.getIdFacultad()
                )
        );

        return mapToResponse(estudiante, token);
    }

    public List<EstudianteResponse> listar(String token) {

        return repo.findAll()
                .stream()
                .map(e -> mapToResponse(e, token))
                .toList();
    }

    public EstudianteResponse obtener(Long id, String token) {

        Estudiante estudiante = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        return mapToResponse(estudiante, token);
    }

    public EstudianteResponse actualizar(Long id, EstudianteDTO dto, String token) {

        Estudiante e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        e.setRut(dto.getRut());
        e.setNombre(dto.getNombre());
        e.setEmail(dto.getEmail());
        e.setCarrera(dto.getCarrera());
        e.setIdFacultad(dto.getIdFacultad());

        return mapToResponse(repo.save(e), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private EstudianteResponse mapToResponse(Estudiante estudiante, String token) {

        var facultad = facultadClient.obtenerFacultad(estudiante.getIdFacultad(), token);

        return EstudianteResponse.builder()
                .id(estudiante.getId())
                .rut(estudiante.getRut())
                .nombre(estudiante.getNombre())
                .email(estudiante.getEmail())
                .carrera(estudiante.getCarrera())
                .facultad(facultad)
                .build();
    }
}
package com.example.ms_profesores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_profesores.client.FacultadClient;
import com.example.ms_profesores.dto.ProfesorDTO;
import com.example.ms_profesores.dto.ProfesorResponse;
import com.example.ms_profesores.model.Profesor;
import com.example.ms_profesores.repository.ProfesorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfesorService {

    private final ProfesorRepository repo;
    private final FacultadClient facultadClient;

    public ProfesorResponse crear(ProfesorDTO dto, String token) {

        var facultad = facultadClient.obtenerFacultad(dto.getIdFacultad(), token);

        if (facultad == null) {
            throw new RuntimeException("Facultad no existe");
        }

        Profesor profesor = repo.save(
                new Profesor(
                        null,
                        dto.getRutP(),
                        dto.getNombreP(),
                        dto.getEmailP(),
                        dto.getEspecialidad(),
                        dto.getIdFacultad()
                )
        );

        return mapToResponse(profesor, token);
    }

    public List<ProfesorResponse> listar(String token) {

        return repo.findAll()
                .stream()
                .map(p -> mapToResponse(p, token))
                .toList();
    }

    public ProfesorResponse obtener(Long id, String token) {

        Profesor profesor = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        return mapToResponse(profesor, token);
    }

    public ProfesorResponse actualizar(Long id, ProfesorDTO dto, String token) {

        Profesor p = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        p.setRutP(dto.getRutP());
        p.setNombreP(dto.getNombreP());
        p.setEmailP(dto.getEmailP());
        p.setEspecialidad(dto.getEspecialidad());
        p.setIdFacultad(dto.getIdFacultad());

        return mapToResponse(repo.save(p), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private ProfesorResponse mapToResponse(Profesor profesor , String token) {

        var facultad = facultadClient.obtenerFacultad(profesor.getIdFacultad(), token);

        return ProfesorResponse.builder()
                .id(profesor.getId())
                .rutP(profesor.getRutP())
                .nombreP(profesor.getNombreP())
                .emailP(profesor.getEmailP())
                .especialidad(profesor.getEspecialidad())
                .facultad(facultad)
                .build();
    }
}
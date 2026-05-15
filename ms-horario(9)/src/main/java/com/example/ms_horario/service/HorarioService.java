package com.example.ms_horario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_horario.client.CursoClient;
import com.example.ms_horario.dto.HorarioDTO;
import com.example.ms_horario.dto.HorarioResponse;
import com.example.ms_horario.model.Horario;
import com.example.ms_horario.repository.HorarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HorarioService {

    private final HorarioRepository repo;
    private final CursoClient cursoClient;

    public HorarioResponse crear(HorarioDTO dto, String token) {

        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        Horario horario = repo.save(
                new Horario(
                        null,
                        dto.getDia(),
                        dto.getHoraInicio(),
                        dto.getHoraFin(),
                        dto.getSala(),
                        dto.getIdCurso()
                )
        );

        return mapToResponse(horario, token);
    }

    public List<HorarioResponse> listar(String token) {

        return repo.findAll()
                .stream()
                .map(h -> mapToResponse(h, token))
                .toList();
    }

    public HorarioResponse obtener(Long id, String token) {

        Horario horario = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado"));

        return mapToResponse(horario, token);
    }

    public HorarioResponse actualizar(Long id, HorarioDTO dto, String token) {

        Horario h = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horario no encontrado"));

        var curso = cursoClient.obtenerCurso(dto.getIdCurso(), token);

        if (curso == null) {
            throw new RuntimeException("Curso no existe");
        }

        h.setDia(dto.getDia());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        h.setSala(dto.getSala());
        h.setIdCurso(dto.getIdCurso());

        return mapToResponse(repo.save(h), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private HorarioResponse mapToResponse(Horario horario, String token) {

        var curso = cursoClient.obtenerCurso(horario.getIdCurso(), token);

        return HorarioResponse.builder()
                .id(horario.getId())
                .dia(horario.getDia())
                .horaInicio(horario.getHoraInicio())
                .horaFin(horario.getHoraFin())
                .sala(horario.getSala())
                .curso(curso)
                .build();
    }
}
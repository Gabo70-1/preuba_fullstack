package com.example.ms_horario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_horario.dto.ApiResponse;
import com.example.ms_horario.dto.HorarioDTO;
import com.example.ms_horario.dto.HorarioResponse;
import com.example.ms_horario.service.HorarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/horarios")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HorarioResponse>> crear(
            @Valid @RequestBody HorarioDTO dto,
            @RequestHeader("Authorization") String token) {

        HorarioResponse horario = service.crear(dto, token);

        return ResponseEntity.status(201).body(
                ApiResponse.<HorarioResponse>builder()
                        .success(true)
                        .message("Horario creado")
                        .data(horario)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<HorarioResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<HorarioResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<HorarioResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<HorarioResponse>builder()
                        .success(true)
                        .message("Horario obtenido")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HorarioResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody HorarioDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<HorarioResponse>builder()
                        .success(true)
                        .message("Horario actualizado")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Horario eliminado")
                        .build()
        );
    }
}
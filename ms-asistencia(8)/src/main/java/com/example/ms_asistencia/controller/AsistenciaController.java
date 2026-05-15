package com.example.ms_asistencia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_asistencia.dto.ApiResponse;
import com.example.ms_asistencia.dto.AsistenciaDTO;
import com.example.ms_asistencia.dto.AsistenciaResponse;
import com.example.ms_asistencia.service.AsistenciaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AsistenciaResponse>> crear(
            @Valid @RequestBody AsistenciaDTO dto,
            @RequestHeader("Authorization") String token) {

        AsistenciaResponse asistencia = service.crear(dto, token);

        return ResponseEntity.status(201).body(
                ApiResponse.<AsistenciaResponse>builder()
                        .success(true)
                        .message("Asistencia creada")
                        .data(asistencia)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<AsistenciaResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<AsistenciaResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<AsistenciaResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<AsistenciaResponse>builder()
                        .success(true)
                        .message("Asistencia obtenida")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AsistenciaResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AsistenciaDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<AsistenciaResponse>builder()
                        .success(true)
                        .message("Asistencia actualizada")
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
                        .message("Asistencia eliminada")
                        .build()
        );
    }
}
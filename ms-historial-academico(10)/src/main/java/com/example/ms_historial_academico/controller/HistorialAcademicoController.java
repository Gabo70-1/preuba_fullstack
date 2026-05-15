package com.example.ms_historial_academico.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_historial_academico.dto.ApiResponse;
import com.example.ms_historial_academico.dto.HistorialAcademicoDTO;
import com.example.ms_historial_academico.dto.HistorialAcademicoResponse;
import com.example.ms_historial_academico.service.HistorialAcademicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/historial-academico")
@RequiredArgsConstructor
public class HistorialAcademicoController {

    private final HistorialAcademicoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HistorialAcademicoResponse>> crear(
            @Valid @RequestBody HistorialAcademicoDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<HistorialAcademicoResponse>builder()
                        .success(true)
                        .message("Historial académico creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<HistorialAcademicoResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<HistorialAcademicoResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<HistorialAcademicoResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<HistorialAcademicoResponse>builder()
                        .success(true)
                        .message("Historial académico obtenido")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<HistorialAcademicoResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody HistorialAcademicoDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<HistorialAcademicoResponse>builder()
                        .success(true)
                        .message("Historial académico actualizado")
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
                        .message("Historial académico eliminado")
                        .build()
        );
    }
}
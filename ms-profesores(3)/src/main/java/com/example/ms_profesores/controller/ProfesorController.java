package com.example.ms_profesores.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_profesores.dto.*;
import com.example.ms_profesores.service.ProfesorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1/profesores")
@RequiredArgsConstructor
public class ProfesorController {

   private final ProfesorService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProfesorResponse>> crear(
            @Valid @RequestBody ProfesorDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<ProfesorResponse>builder()
                        .success(true)
                        .message("Estudiante creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<ProfesorResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<ProfesorResponse>>builder()
                        .success(true)
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<ProfesorResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<ProfesorResponse>builder()
                        .success(true)
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProfesorResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProfesorDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<ProfesorResponse>builder()
                        .success(true)
                        .message("Estudiante actualizado")
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
                        .message("Estudiante eliminado")
                        .build()
        );
    }
}
package com.example.ms_evaluaciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_evaluaciones.dto.ApiResponse;
import com.example.ms_evaluaciones.dto.EvaluacionDTO;
import com.example.ms_evaluaciones.dto.EvaluacionResponse;
import com.example.ms_evaluaciones.service.EvaluacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EvaluacionResponse>> crear(
            @Valid @RequestBody EvaluacionDTO dto,
            @RequestHeader("Authorization") String token) {

        EvaluacionResponse evaluacion = service.crear(dto, token);

        return ResponseEntity.status(201).body(
                ApiResponse.<EvaluacionResponse>builder()
                        .success(true)
                        .message("Evaluación creada")
                        .data(evaluacion)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<EvaluacionResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<EvaluacionResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<EvaluacionResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<EvaluacionResponse>builder()
                        .success(true)
                        .message("Evaluación obtenida")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EvaluacionResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EvaluacionDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<EvaluacionResponse>builder()
                        .success(true)
                        .message("Evaluación actualizada")
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
                        .message("Evaluación eliminada")
                        .build()
        );
    }
}
package com.example.ms_notas.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_notas.dto.ApiResponse;
import com.example.ms_notas.dto.NotaDTO;
import com.example.ms_notas.dto.NotaResponse;
import com.example.ms_notas.service.NotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NotaResponse>> crear(
            @Valid @RequestBody NotaDTO dto,
            @RequestHeader("Authorization") String token) {

        NotaResponse nota = service.crear(dto, token);

        return ResponseEntity.status(201).body(
                ApiResponse.<NotaResponse>builder()
                        .success(true)
                        .message("Nota creada")
                        .data(nota)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<NotaResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<NotaResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<NotaResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<NotaResponse>builder()
                        .success(true)
                        .message("Nota obtenida")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NotaResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody NotaDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<NotaResponse>builder()
                        .success(true)
                        .message("Nota actualizada")
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
                        .message("Nota eliminada")
                        .build()
        );
    }
}
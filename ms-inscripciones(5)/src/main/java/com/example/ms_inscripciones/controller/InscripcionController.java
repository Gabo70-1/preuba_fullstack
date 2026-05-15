package com.example.ms_inscripciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_inscripciones.dto.ApiResponse;
import com.example.ms_inscripciones.dto.InscripcionDTO;
import com.example.ms_inscripciones.dto.InscripcionResponse;
import com.example.ms_inscripciones.service.InscripcionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InscripcionResponse>> crear(
            @Valid @RequestBody InscripcionDTO dto,
            @RequestHeader("Authorization") String token) {

        InscripcionResponse inscripcion = service.crear(dto, token);

        return ResponseEntity.status(201).body(
                ApiResponse.<InscripcionResponse>builder()
                        .success(true)
                        .message("Inscripción creada")
                        .data(inscripcion)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<InscripcionResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<InscripcionResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<InscripcionResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<InscripcionResponse>builder()
                        .success(true)
                        .message("Inscripción obtenida")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<InscripcionResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InscripcionDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<InscripcionResponse>builder()
                        .success(true)
                        .message("Inscripción actualizada")
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
                        .message("Inscripción eliminada")
                        .build()
        );
    }
}
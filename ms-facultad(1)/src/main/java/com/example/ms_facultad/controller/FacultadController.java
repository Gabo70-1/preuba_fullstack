package com.example.ms_facultad.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_facultad.dto.ApiResponse;
import com.example.ms_facultad.dto.FacultadDTO;
import com.example.ms_facultad.model.Facultad;
import com.example.ms_facultad.service.FacultadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/facultades")
@RequiredArgsConstructor
public class FacultadController {

    private final FacultadService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Facultad>> crear(@Valid @RequestBody FacultadDTO dto) {

        Facultad facultad = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Facultad>builder()
                        .success(true)
                        .message("Facultad creada")
                        .data(facultad)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Facultad>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Facultad>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Facultad>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Facultad>builder()
                        .success(true)
                        .message("Facultad obtenida")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Facultad>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody FacultadDTO dto) {

        Facultad facultad = service.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Facultad>builder()
                        .success(true)
                        .message("Facultad actualizada")
                        .data(facultad)
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
                        .message("Facultad eliminada")
                        .build()
        );
    }
}
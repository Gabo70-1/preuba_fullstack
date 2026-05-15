package com.example.ms_cursos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cursos.dto.ApiResponse;
import com.example.ms_cursos.dto.CursoDTO;
import com.example.ms_cursos.dto.CursoResponse;
import com.example.ms_cursos.service.CursoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CursoResponse>> crear(
            @Valid @RequestBody CursoDTO dto,
            @RequestHeader("Authorization") String token) {

        CursoResponse curso = service.crear(dto, token);

        return ResponseEntity.status(201).body(
                ApiResponse.<CursoResponse>builder()
                        .success(true)
                        .message("Curso creado")
                        .data(curso)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<CursoResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<CursoResponse>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<CursoResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<CursoResponse>builder()
                        .success(true)
                        .message("Curso obtenido")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CursoResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoDTO dto,
            @RequestHeader("Authorization") String token) {

        CursoResponse curso = service.actualizar(id, dto, token);

        return ResponseEntity.ok(
                ApiResponse.<CursoResponse>builder()
                        .success(true)
                        .message("Curso actualizado")
                        .data(curso)
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
                        .message("Curso eliminado")
                        .build()
        );
    }
}
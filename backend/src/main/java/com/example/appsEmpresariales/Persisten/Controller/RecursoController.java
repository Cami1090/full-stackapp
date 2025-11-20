package com.example.appsEmpresariales.Persisten.Controller;

import com.example.appsEmpresariales.Persisten.Serviceimpls.RecursoService;
import com.example.appsEmpresariales.domain.dto.RecursoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@Tag(name = "Recursos", description = "API para la gestión de recursos")
public class RecursoController {

    private final RecursoService recursoService;

    @Autowired
    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    // -------- CRUD --------
    @GetMapping
    @Operation(summary = "Obtener todos los recursos", description = "Devuelve una lista de todos los recursos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recursos obtenidos con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<RecursoDTO>> getAllRecursos() {
        return ResponseEntity.ok(recursoService.obtenerTodosLosRecursos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un recurso por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recurso encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    public ResponseEntity<RecursoDTO> getRecursoById(
            @PathVariable @Parameter(description = "ID del recurso") String id) {
        RecursoDTO recurso = recursoService.obtenerRecursoPorId(id);
        return recurso != null ? ResponseEntity.ok(recurso) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo recurso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recurso creado con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecursoDTO.class)))
    })
    public ResponseEntity<RecursoDTO> crearRecurso(
            @RequestBody @Parameter(description = "Datos del recurso a crear") RecursoDTO recurso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recursoService.guardarRecurso(recurso));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un recurso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recurso actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    public ResponseEntity<RecursoDTO> actualizarRecurso(
            @PathVariable String id,
            @RequestBody RecursoDTO recurso) {
        recurso.setId(id);
        return ResponseEntity.ok(recursoService.actualizarRecurso(recurso));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar recurso por ID", description = "Elimina el recurso por el ID seleccionado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Recurso eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    public ResponseEntity<Void> eliminarRecurso(@PathVariable String id) {
        recursoService.eliminarRecurso(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Búsquedas --------
    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Obtener recursos por tipo")
    @ApiResponse(responseCode = "200", description = "Recursos obtenidos con éxito")
    public ResponseEntity<List<RecursoDTO>> getRecursosPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(recursoService.obtenerRecursosPorTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener recursos por estado")
    @ApiResponse(responseCode = "200", description = "Recursos obtenidos con éxito")
    public ResponseEntity<List<RecursoDTO>> getRecursosPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(recursoService.obtenerRecursosPorEstado(estado));
    }

}
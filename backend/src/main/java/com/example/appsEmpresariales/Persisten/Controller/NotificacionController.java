package com.example.appsEmpresariales.Persisten.Controller;

import com.example.appsEmpresariales.Persisten.Serviceimpls.NotificacionService;
import com.example.appsEmpresariales.domain.dto.NotificacionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "API para la gestión de notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    // -------- CRUD --------
    @PostMapping
    @Operation(summary = "Crear una notificación")
    @ApiResponse(responseCode = "201", description = "Notificación creada con éxito",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NotificacionDTO.class)))
    public ResponseEntity<NotificacionDTO> crearNotificacion(
            @RequestBody @Parameter(description = "Datos de la notificación") NotificacionDTO notificacion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.guardarNotificacion(notificacion));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las notificaciones")
    @ApiResponse(responseCode = "200", description = "Notificaciones obtenidas con éxito")
    public ResponseEntity<List<NotificacionDTO>> obtenerTodas() {
        return ResponseEntity.ok(notificacionService.obtenerTodas());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una notificación existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación actualizada con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificacionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<NotificacionDTO> actualizarNotificacion(
            @PathVariable String id,
            @RequestBody NotificacionDTO notificacion) {
        notificacion.setId(id);
        NotificacionDTO actualizada = notificacionService.guardarNotificacion(notificacion);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una notificación por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notificación eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable String id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Búsquedas --------
    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener notificaciones por usuario")
    @ApiResponse(responseCode = "200", description = "Notificaciones obtenidas con éxito")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorUsuario(idUsuario));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Obtener notificaciones por tipo")
    @ApiResponse(responseCode = "200", description = "Notificaciones obtenidas con éxito")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorTipo(tipo));
    }
}

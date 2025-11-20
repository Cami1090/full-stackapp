package com.example.appsEmpresariales.Persisten.Controller;

import com.example.appsEmpresariales.Persisten.Serviceimpls.ReservaService;
import com.example.appsEmpresariales.domain.dto.ReservaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "API para la gestión de reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // -------- CRUD --------
    @GetMapping
    @Operation(
            summary = "Obtener todas las reservas",
            description = "Devuelve una lista con todas las reservas registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas con éxito"),
            @ApiResponse(responseCode = "204", description = "No existen reservas registradas"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservaDTO>> getAllReservas() {
        return ResponseEntity.ok(reservaService.obtenerTodasLasReservas());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una reserva por ID",
            description = "Devuelve la información de una reserva específica mediante su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es válido"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaDTO> getReservaById(
            @PathVariable @Parameter(description = "ID de la reserva") String id) {
        ReservaDTO reserva = reservaService.obtenerReservaPorId(id);
        return reserva != null ? ResponseEntity.ok(reserva) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva reserva",
            description = "Crea una nueva reserva en el sistema a partir de los datos enviados en la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear la reserva"),
            @ApiResponse(responseCode = "409", description = "Conflicto: la reserva ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaDTO> crearReserva(
            @RequestBody @Parameter(description = "Datos de la reserva a crear") ReservaDTO reserva) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.guardarReserva(reserva));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una reserva",
            description = "Modifica los datos de una reserva existente identificada por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaDTO> actualizarReserva(
            @PathVariable String id,
            @RequestBody ReservaDTO reserva) {
        reserva.setId(id);
        return ResponseEntity.ok(reservaService.actualizarReserva(reserva));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una reserva por ID",
            description = "Elimina una reserva existente del sistema mediante su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada con éxito"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es válido"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarReserva(@PathVariable String id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Búsquedas --------
    @GetMapping("/usuario/{usuario}")
    @Operation(
            summary = "Obtener reservas por usuario",
            description = "Devuelve todas las reservas asociadas a un usuario específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "400", description = "Usuario inválido"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reservas para el usuario"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservaDTO>> getReservasPorUsuario(@PathVariable String usuario) {
        return ResponseEntity.ok(reservaService.obtenerReservasPorUsuario(usuario));
    }

    @GetMapping("/estado/{estado}")
    @Operation(
            summary = "Obtener reservas por estado",
            description = "Devuelve todas las reservas que se encuentran en el estado especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reservas con ese estado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservaDTO>> getReservasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reservaService.obtenerReservasPorEstado(estado));
    }

    @GetMapping("/recurso/{recurso}")
    @Operation(
            summary = "Obtener reservas por recurso",
            description = "Devuelve todas las reservas asociadas a un recurso específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "400", description = "Recurso inválido"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reservas para ese recurso"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReservaDTO>> getReservasPorRecurso(@PathVariable String recurso) {
        return ResponseEntity.ok(reservaService.obtenerReservasPorRecurso(recurso));
    }



    // -------- Utilitarios --------
    @GetMapping("/existe/{id}")
    @Operation(
            summary = "Verificar si existe una reserva",
            description = "Devuelve **true** si la reserva con el ID proporcionado existe, en caso contrario **false**."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado de la verificación"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> existeReserva(@PathVariable String id) {
        return ResponseEntity.ok(reservaService.existeReserva(id));
    }

    @GetMapping("/count")
    @Operation(
            summary = "Contar todas las reservas",
            description = "Devuelve el número total de reservas registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Número total de reservas devuelto con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reservas en el sistema")
    })
    public ResponseEntity<Long> contarReservas() {
        return ResponseEntity.ok(reservaService.contarReservas());
    }

    @GetMapping("/count/estado/{estado}")
    @Operation(
            summary = "Contar reservas por estado",
            description = "Devuelve la cantidad de reservas que se encuentran en un estado específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Número de reservas por estado obtenido con éxito"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "404", description = "No existen reservas con ese estado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Long> contarReservasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reservaService.contarReservasPorEstado(estado));
    }
}

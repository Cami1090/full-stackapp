package com.example.appsEmpresariales.Persisten.Controller;

import com.example.appsEmpresariales.Persisten.Serviceimpls.PagoService;
import com.example.appsEmpresariales.domain.dto.PagoDTO;
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
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "API para la gestión de pagos")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // -------- CRUD --------
    @GetMapping
    @Operation(
            summary = "Obtener todos los pagos",
            description = "Este endpoint devuelve una lista con todos los pagos registrados en el sistema, incluyendo su estado, monto y fecha."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagos obtenidos con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "204", description = "No existen pagos registrados"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PagoDTO>> getAllPagos() {
        return ResponseEntity.ok(pagoService.obtenerTodosLosPagos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un pago por ID",
            description = "Devuelve los datos de un pago específico según el ID proporcionado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagoDTO> getPagoById(
            @PathVariable @Parameter(description = "ID del pago a consultar") String id) {
        PagoDTO pago = pagoService.obtenerPagoPorId(id);
        return pago != null ? ResponseEntity.ok(pago) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo pago",
            description = "Permite registrar un nuevo pago en el sistema enviando los datos correspondientes en el cuerpo de la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para la creación del pago"),
            @ApiResponse(responseCode = "409", description = "Conflicto: el pago ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagoDTO> crearPago(
            @RequestBody @Parameter(description = "Datos del pago a registrar") PagoDTO pago) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardarPago(pago));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un pago existente",
            description = "Modifica los datos de un pago ya registrado, identificándolo mediante su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para la actualización"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagoDTO> actualizarPago(
            @PathVariable String id,
            @RequestBody PagoDTO pago) {
        pago.setId(id);
        PagoDTO actualizado = pagoService.actualizarPago(pago);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un pago por ID",
            description = "Elimina de manera permanente el registro de un pago identificado por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarPago(@PathVariable String id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Búsqueda --------
    @GetMapping("/estado/{estado}")
    @Operation(
            summary = "Obtener pagos por estado",
            description = "Devuelve una lista de pagos filtrados según el estado proporcionado (ejemplo: PENDIENTE, COMPLETADO, CANCELADO)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagos obtenidos con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagoDTO.class))),
            @ApiResponse(responseCode = "204", description = "No existen pagos con el estado especificado"),
            @ApiResponse(responseCode = "400", description = "Estado inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PagoDTO>> getPagosPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorEstado(estado));
    }
}

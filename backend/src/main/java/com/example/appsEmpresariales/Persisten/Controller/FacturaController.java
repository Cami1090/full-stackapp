package com.example.appsEmpresariales.Persisten.Controller;

import com.example.appsEmpresariales.Persisten.Serviceimpls.FacturaService;
import com.example.appsEmpresariales.domain.dto.FacturaDTO;
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
@RequestMapping("/api/facturas")
@Tag(name = "Facturas", description = "API para la gestión de facturas de clientes")
public class FacturaController {

    private final FacturaService facturaService;

    @Autowired
    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    // -------- CRUD --------
    @GetMapping
    @Operation(
            summary = "Obtener todas las facturas",
            description = "Devuelve un listado completo con todas las facturas registradas en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de facturas obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "No se encontraron facturas registradas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        return ResponseEntity.ok(facturaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una factura por ID",
            description = "Busca y devuelve la información de una factura específica a partir de su identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaDTO.class))),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es válido"),
            @ApiResponse(responseCode = "404", description = "No existe una factura con el ID especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<FacturaDTO> getFacturaById(
            @PathVariable @Parameter(description = "ID de la factura") String id) {
        FacturaDTO factura = facturaService.findById(id);
        return factura != null ? ResponseEntity.ok(factura) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva factura",
            description = "Registra una nueva factura en el sistema a partir de los datos enviados en la solicitud."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Factura creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Los datos proporcionados son inválidos o incompletos"),
            @ApiResponse(responseCode = "404", description = "No se pudo crear la factura porque falta información relacionada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<FacturaDTO> crearFactura(
            @RequestBody @Parameter(description = "Datos de la factura a crear") FacturaDTO factura) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.save(factura));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una factura existente",
            description = "Modifica los datos de una factura ya registrada en el sistema usando su ID como referencia."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaDTO.class))),
            @ApiResponse(responseCode = "400", description = "El ID o los datos de la factura no son válidos"),
            @ApiResponse(responseCode = "404", description = "No existe una factura con el ID especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<FacturaDTO> actualizarFactura(
            @PathVariable String id,
            @RequestBody FacturaDTO factura) {
        factura.setId(id);
        FacturaDTO actualizada = facturaService.update(factura);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una factura por ID",
            description = "Elimina del sistema la factura asociada al ID proporcionado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Factura eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es válido"),
            @ApiResponse(responseCode = "404", description = "No existe una factura con el ID especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarFactura(@PathVariable String id) {
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Búsqueda específica --------
    @GetMapping("/numero/{numeroFactura}")
    @Operation(
            summary = "Obtener una factura por número de factura",
            description = "Busca una factura en el sistema utilizando su número único de factura."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaDTO.class))),
            @ApiResponse(responseCode = "400", description = "El número de factura proporcionado no es válido"),
            @ApiResponse(responseCode = "404", description = "No existe una factura con el número especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<FacturaDTO> getFacturaByNumero(
            @PathVariable @Parameter(description = "Número de la factura") String numeroFactura) {
        FacturaDTO factura = facturaService.findByNumeroFactura(numeroFactura);
        return factura != null ? ResponseEntity.ok(factura) : ResponseEntity.notFound().build();
    }
}

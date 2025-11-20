package com.example.appsEmpresariales.Persisten.Controller;

import com.example.appsEmpresariales.Persisten.Serviceimpls.UsuarioService;
import com.example.appsEmpresariales.domain.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios del sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // -------- CRUD --------
    @GetMapping
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Devuelve una lista con todos los usuarios registrados en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos con éxito"),
            @ApiResponse(responseCode = "204", description = "No existen usuarios registrados"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un usuario por ID",
            description = "Devuelve la información del usuario cuyo ID se proporciona."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "400", description = "ID de usuario inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioDTO> getUsuarioById(
            @PathVariable @Parameter(description = "ID del usuario a consultar") String id) {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema a partir de la información proporcionada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "409", description = "El email o ID ya está registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioDTO> guardarUsuario(
            @RequestBody @Parameter(description = "Datos del usuario a registrar") UsuarioDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuario));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un usuario existente",
            description = "Modifica los datos de un usuario existente identificado por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable @Parameter(description = "ID del usuario a actualizar") String id,
            @RequestBody @Parameter(description = "Datos actualizados del usuario") UsuarioDTO usuario) {

        usuario.setId(id);
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(usuario);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina permanentemente a un usuario del sistema a partir de su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID de usuario inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable @Parameter(description = "ID del usuario a eliminar") String id) {

        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // -------- Consultas personalizadas --------
    @GetMapping("/rol/{rol}")
    @Operation(
            summary = "Buscar usuarios por rol",
            description = "Devuelve una lista de usuarios que tienen el rol especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados con éxito"),
            @ApiResponse(responseCode = "400", description = "Rol inválido"),
            @ApiResponse(responseCode = "404", description = "No se encontraron usuarios con ese rol"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UsuarioDTO>> getUsuariosPorRol(
            @PathVariable @Parameter(description = "Rol a consultar (ejemplo: ADMIN, CLIENTE)") String rol) {
        List<UsuarioDTO> usuarios = usuarioService.obtenerUsuariosPorRol(rol);
        return usuarios.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuarios);
    }

    @GetMapping("/exists/email/{email}")
    @Operation(
            summary = "Verificar existencia de un usuario por email",
            description = "Devuelve **true** si existe un usuario registrado con el email proporcionado, de lo contrario **false**."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificación realizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Email con formato inválido"),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese email"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Boolean> existeUsuarioPorEmail(
            @PathVariable @Parameter(description = "Email a verificar") String email) {
        boolean existe = usuarioService.existeUsuarioPorEmail(email);
        return ResponseEntity.ok(existe);
    }

    // -------- Autenticación --------
    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuario",
            description = "Valida las credenciales de un usuario y devuelve su información si son correctas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    public ResponseEntity<UsuarioDTO> login(
            @RequestBody @Parameter(description = "Credenciales de acceso (email y contraseña)") LoginRequest request) {
        UsuarioDTO usuario = usuarioService.autenticarUsuario(request.getEmail(), request.getContraseña());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Clase interna para el request de login
    public static class LoginRequest {
        private String email;
        private String contraseña;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    }
}

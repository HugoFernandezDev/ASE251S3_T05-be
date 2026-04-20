package Agropacayales.valleGrande.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import Agropacayales.valleGrande.model.Usuario;
import Agropacayales.valleGrande.service.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@Tag(name = "Usuario-Controller", description = "Operaciones de gestión de usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene la lista de todos los usuarios registrados")
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene un usuario específico por su ID")
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario nuevo = service.guardar(usuario);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(nuevo.getIdUsuario()).toUri();
        return ResponseEntity.created(location).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente")
    public ResponseEntity<Usuario> editar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.actualizar(id, usuario));
    }

    @PatchMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar (Lógico)", description = "Desactiva al usuario cambiando su estado a false")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminarLogico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/restaurar/{id}")
    @Operation(summary = "Restaurar", description = "Activa nuevamente al usuario cambiando su estado a true")
    public ResponseEntity<Void> restaurar(@PathVariable Integer id) {
        service.restaurarLogico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
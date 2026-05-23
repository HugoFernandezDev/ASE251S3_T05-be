package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.model.Cultivo;
import Agropacayales.valleGrande.service.CultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/cultivos")
@Tag(name = "Cultivo-Controller", description = "Gestion de ciclos de cultivo y siembra")
public class CultivoController {

    @Autowired
    private CultivoService cultivoService;

    @GetMapping
    @Operation(summary = "Listar cultivos", description = "Obtiene la lista de todos los cultivos registrados")
    public ResponseEntity<List<Cultivo>> listarTodos() {
        return ResponseEntity.ok(cultivoService.listarTodos());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar por estado", description = "Filtra cultivos activos o inactivos")
    public ResponseEntity<List<Cultivo>> listarPorEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(cultivoService.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene un cultivo especifico por su ID")
    public ResponseEntity<Cultivo> listarPorId(@PathVariable Long id) {
        return cultivoService.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear cultivo", description = "Registra un nuevo ciclo de cultivo")
    public ResponseEntity<Cultivo> crear(@RequestBody Cultivo cultivo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cultivoService.crear(cultivo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar cultivo", description = "Modifica los datos de un cultivo existente")
    public ResponseEntity<Cultivo> editar(@PathVariable Long id, @RequestBody Cultivo cultivo) {
        Cultivo editado = cultivoService.editar(id, cultivo);
        return (editado != null) ? ResponseEntity.ok(editado) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar (Logico)", description = "Desactiva el cultivo cambiando su estado a false")
    public ResponseEntity<Cultivo> eliminar(@PathVariable Long id) {
        Cultivo eliminado = cultivoService.eliminar(id);
        return (eliminado != null) ? ResponseEntity.ok(eliminado) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar", description = "Reactiva el cultivo cambiando su estado a true")
    public ResponseEntity<Cultivo> restaurar(@PathVariable Long id) {
        Cultivo restaurado = cultivoService.restaurar(id);
        return (restaurado != null) ? ResponseEntity.ok(restaurado) : ResponseEntity.notFound().build();
    }
}

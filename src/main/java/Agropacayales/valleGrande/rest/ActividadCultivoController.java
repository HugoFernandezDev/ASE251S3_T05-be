package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.dto.request.ActividadCultivoRequestDto;
import Agropacayales.valleGrande.dto.response.ActividadCultivoResponseDto;
import Agropacayales.valleGrande.service.IActividadCultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/actividades-cultivos")
@Tag(name = "Actividad-Cultivo-Controller", description = "Gestion de transacciones de actividades agrícolas en cultivos")
public class ActividadCultivoController {

    @Autowired
    private IActividadCultivoService service;

    @PostMapping
    @Operation(summary = "Registrar actividad", description = "Registra una nueva actividad y sus insumos consumidos en una sola acción transaccional")
    public ResponseEntity<ActividadCultivoResponseDto> registrar(@RequestBody ActividadCultivoRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarActividad(request));
    }

    @GetMapping
    @Operation(summary = "Listar todas las actividades", description = "Obtiene la lista de todas las actividades registradas")
    public ResponseEntity<List<ActividadCultivoResponseDto>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtrar por estado", description = "Obtiene la lista de actividades por estado (activo/inactivo)")
    public ResponseEntity<List<ActividadCultivoResponseDto>> listarPorEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene los detalles de una actividad específica por su ID")
    public ResponseEntity<ActividadCultivoResponseDto> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar (Lógico)", description = "Realiza la eliminación lógica de una actividad cambiando su estado a false")
    public ResponseEntity<ActividadCultivoResponseDto> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.eliminar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar", description = "Restaura el estado activo de una actividad")
    public ResponseEntity<ActividadCultivoResponseDto> restaurar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.restaurar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

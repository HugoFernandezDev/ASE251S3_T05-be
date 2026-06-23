package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.model.Insumo;
import Agropacayales.valleGrande.service.InsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insumos")
@Tag(name = "Insumo-Controller", description = "Operaciones de gestión del maestro de insumos agrícolas")
public class InsumoController {

    @Autowired
    private InsumoService insumoService;

    // GET - Listar todos los insumos
    @GetMapping
    @Operation(summary = "Listar todos los insumos", description = "Obtiene una lista con todos los insumos registrados en el sistema")
    public ResponseEntity<List<Insumo>> listarTodos() {
        List<Insumo> insumos = insumoService.listarTodos();
        return ResponseEntity.ok(insumos);
    }

    // GET - Listar insumo por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener insumo por ID", description = "Busca un insumo específico por su identificador")
    @ApiResponse(responseCode = "200", description = "Insumo encontrado")
    @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    public ResponseEntity<Insumo> listarPorId(@PathVariable Long id) {
        Optional<Insumo> insumo = insumoService.listarPorId(id);
        return insumo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar insumos por estado (activos: true, inactivos: false)
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar insumos por estado", description = "Filtra los insumos activos o inactivos")
    public ResponseEntity<List<Insumo>> listarPorEstado(@PathVariable Boolean estado) {
        List<Insumo> insumos = insumoService.listarPorEstado(estado);
        return ResponseEntity.ok(insumos);
    }

    // POST - Crear nuevo insumo
    @PostMapping
    @Operation(summary = "Registrar nuevo insumo", description = "Crea un insumo en el sistema, validando integridad de datos y unicidad")
    @ApiResponse(responseCode = "201", description = "Insumo creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o nombre de insumo duplicado")
    public ResponseEntity<Insumo> crear(@Valid @RequestBody Insumo insumo) {
        Insumo nuevoInsumo = insumoService.crear(insumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInsumo);
    }

    // PUT - Editar insumo existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar insumo existente", description = "Modifica los datos de un insumo activo existente identificándolo por su ID")
    @ApiResponse(responseCode = "200", description = "Insumo actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o conflicto de nombre")
    @ApiResponse(responseCode = "404", description = "Insumo no encontrado o inactivo")
    public ResponseEntity<Insumo> editar(@PathVariable Long id, @Valid @RequestBody Insumo insumo) {
        Insumo insumoEditado = insumoService.editar(id, insumo);
        if (insumoEditado != null) {
            return ResponseEntity.ok(insumoEditado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Eliminar lógico (cambiar estado a false)
    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar insumo (Lógico)", description = "Desactiva un insumo cambiando su estado a false, validando que no tenga stock")
    @ApiResponse(responseCode = "200", description = "Insumo eliminado lógicamente")
    @ApiResponse(responseCode = "400", description = "Conflicto por existencia de stock")
    @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    public ResponseEntity<Insumo> eliminar(@PathVariable Long id) {
        Insumo insumoEliminado = insumoService.eliminar(id);
        if (insumoEliminado != null) {
            return ResponseEntity.ok(insumoEliminado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Restaurar lógico (cambiar estado a true)
    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar insumo", description = "Activa nuevamente un insumo previamente inactivo")
    @ApiResponse(responseCode = "200", description = "Insumo restaurado exitosamente")
    @ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    public ResponseEntity<Insumo> restaurar(@PathVariable Long id) {
        Insumo insumoRestaurado = insumoService.restaurar(id);
        if (insumoRestaurado != null) {
            return ResponseEntity.ok(insumoRestaurado);
        }
        return ResponseEntity.notFound().build();
    }

    // GET - Buscar insumos por nombre
    @GetMapping("/buscar")
    @Operation(summary = "Buscar insumos por nombre", description = "Busca insumos cuyo nombre contenga la cadena proporcionada (ignora mayúsculas/minúsculas)")
    public ResponseEntity<List<Insumo>> buscarPorNombre(@RequestParam String nombre) {
        List<Insumo> insumos = insumoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(insumos);
    }

    // GET - Filtrar insumos por tipo
    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar insumos por tipo", description = "Filtra insumos según su categoría o tipo (ignora mayúsculas/minúsculas)")
    public ResponseEntity<List<Insumo>> filtrarPorTipo(@RequestParam String tipo) {
        List<Insumo> insumos = insumoService.filtrarPorTipo(tipo);
        return ResponseEntity.ok(insumos);
    }

    // GET - Listar insumos paginados
    @GetMapping("/paginado")
    @Operation(summary = "Listar insumos con paginación", description = "Obtiene una página de insumos indicando tamaño de página, número de página y ordenación")
    public ResponseEntity<Page<Insumo>> listarPaginado(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<Insumo> insumos = insumoService.listarPaginado(pageable);
        return ResponseEntity.ok(insumos);
    }
}

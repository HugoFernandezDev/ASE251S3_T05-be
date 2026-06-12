package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.model.Producto;
import Agropacayales.valleGrande.service.ProductoService;
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
@RequestMapping("/api/productos")
@Tag(name = "Producto-Controller", description = "Operaciones de gestión del maestro de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET - Listar todos los productos
    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista con todos los productos registrados en el sistema")
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    // GET - Listar producto por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico por su identificador")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<Producto> listarPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.listarPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar productos por estado (activos: true, inactivos: false)
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar productos por estado", description = "Filtra los productos activos o inactivos")
    public ResponseEntity<List<Producto>> listarPorEstado(@PathVariable Boolean estado) {
        List<Producto> productos = productoService.listarPorEstado(estado);
        return ResponseEntity.ok(productos);
    }

    // POST - Crear nuevo producto
    @PostMapping
    @Operation(summary = "Registrar nuevo producto", description = "Crea un producto en el sistema, validando integridad de datos")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o nombre de producto duplicado")
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // PUT - Editar producto existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto existente", description = "Modifica los datos de un producto existente identificándolo por su ID")
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o conflicto de nombre")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<Producto> editar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Producto productoEditado = productoService.editar(id, producto);
        if (productoEditado != null) {
            return ResponseEntity.ok(productoEditado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Eliminar lógico (cambiar estado a false)
    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar producto (Lógico)", description = "Desactiva un producto cambiando su estado a false")
    @ApiResponse(responseCode = "200", description = "Producto eliminado lógicamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<Producto> eliminar(@PathVariable Long id) {
        Producto productoEliminado = productoService.eliminar(id);
        if (productoEliminado != null) {
            return ResponseEntity.ok(productoEliminado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Restaurar lógico (cambiar estado a true)
    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar producto", description = "Activa nuevamente un producto previamente eliminado lógicamente")
    @ApiResponse(responseCode = "200", description = "Producto restaurado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<Producto> restaurar(@PathVariable Long id) {
        Producto productoRestaurado = productoService.restaurar(id);
        if (productoRestaurado != null) {
            return ResponseEntity.ok(productoRestaurado);
        }
        return ResponseEntity.notFound().build();
    }

    // GET - Buscar productos por nombre
    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre", description = "Busca productos cuyo nombre contenga la cadena proporcionada (ignora mayúsculas/minúsculas)")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    // GET - Filtrar productos por tipo
    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar productos por tipo", description = "Filtra productos según su categoría o tipo (ignora mayúsculas/minúsculas)")
    public ResponseEntity<List<Producto>> filtrarPorTipo(@RequestParam String tipo) {
        List<Producto> productos = productoService.filtrarPorTipo(tipo);
        return ResponseEntity.ok(productos);
    }

    // GET - Listar productos paginados
    @GetMapping("/paginado")
    @Operation(summary = "Listar productos con paginación", description = "Obtiene una página de productos indicando tamaño de página, número de página y ordenación")
    public ResponseEntity<Page<Producto>> listarPaginado(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<Producto> productos = productoService.listarPaginado(pageable);
        return ResponseEntity.ok(productos);
    }
}

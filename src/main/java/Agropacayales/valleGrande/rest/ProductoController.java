package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.model.Producto;
import Agropacayales.valleGrande.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET - Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    // GET - Listar producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> listarPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.listarPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar productos por estado (activos: true, inactivos: false)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Producto>> listarPorEstado(@PathVariable Boolean estado) {
        List<Producto> productos = productoService.listarPorEstado(estado);
        return ResponseEntity.ok(productos);
    }

    // POST - Crear nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // PUT - Editar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> editar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoEditado = productoService.editar(id, producto);
        if (productoEditado != null) {
            return ResponseEntity.ok(productoEditado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Eliminar lógico (cambiar estado a false)
    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<Producto> eliminar(@PathVariable Long id) {
        Producto productoEliminado = productoService.eliminar(id);
        if (productoEliminado != null) {
            return ResponseEntity.ok(productoEliminado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Restaurar lógico (cambiar estado a true)
    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<Producto> restaurar(@PathVariable Long id) {
        Producto productoRestaurado = productoService.restaurar(id);
        if (productoRestaurado != null) {
            return ResponseEntity.ok(productoRestaurado);
        }
        return ResponseEntity.notFound().build();
    }
}

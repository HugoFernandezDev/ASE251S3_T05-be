package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.model.Insumo;
import Agropacayales.valleGrande.service.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    @Autowired
    private InsumoService insumoService;

    // GET - Listar todos los insumos
    @GetMapping
    public ResponseEntity<List<Insumo>> listarTodos() {
        List<Insumo> insumos = insumoService.listarTodos();
        return ResponseEntity.ok(insumos);
    }

    // GET - Listar insumo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Insumo> listarPorId(@PathVariable Long id) {
        Optional<Insumo> insumo = insumoService.listarPorId(id);
        return insumo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar insumos por estado (activos: true, inactivos: false)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Insumo>> listarPorEstado(@PathVariable Boolean estado) {
        List<Insumo> insumos = insumoService.listarPorEstado(estado);
        return ResponseEntity.ok(insumos);
    }

    // POST - Crear nuevo insumo
    @PostMapping
    public ResponseEntity<Insumo> crear(@RequestBody Insumo insumo) {
        Insumo nuevoInsumo = insumoService.crear(insumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInsumo);
    }

    // PUT - Editar insumo existente
    @PutMapping("/{id}")
    public ResponseEntity<Insumo> editar(@PathVariable Long id, @RequestBody Insumo insumo) {
        Insumo insumoEditado = insumoService.editar(id, insumo);
        if (insumoEditado != null) {
            return ResponseEntity.ok(insumoEditado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Eliminar lógico (cambiar estado a false)
    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<Insumo> eliminar(@PathVariable Long id) {
        Insumo insumoEliminado = insumoService.eliminar(id);
        if (insumoEliminado != null) {
            return ResponseEntity.ok(insumoEliminado);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Restaurar lógico (cambiar estado a true)
    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<Insumo> restaurar(@PathVariable Long id) {
        Insumo insumoRestaurado = insumoService.restaurar(id);
        if (insumoRestaurado != null) {
            return ResponseEntity.ok(insumoRestaurado);
        }
        return ResponseEntity.notFound().build();
    }
}

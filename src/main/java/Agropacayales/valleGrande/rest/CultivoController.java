package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.model.Cultivo;
import Agropacayales.valleGrande.service.CultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cultivos")
@CrossOrigin(origins = "*")
public class CultivoController {

    @Autowired
    private CultivoService cultivoService;

    @GetMapping
    public ResponseEntity<List<Cultivo>> listarTodos() {
        return ResponseEntity.ok(cultivoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cultivo> listarPorId(@PathVariable Long id) {
        Optional<Cultivo> cultivo = cultivoService.listarPorId(id);
        return cultivo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cultivo>> listarPorEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(cultivoService.listarPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Cultivo> crear(@RequestBody Cultivo cultivo) {
        Cultivo nuevoCultivo = cultivoService.crear(cultivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCultivo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cultivo> editar(@PathVariable Long id, @RequestBody Cultivo cultivo) {
        Cultivo cultivoEditado = cultivoService.editar(id, cultivo);
        if (cultivoEditado != null) {
            return ResponseEntity.ok(cultivoEditado);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<Cultivo> eliminar(@PathVariable Long id) {
        Cultivo cultivoEliminado = cultivoService.eliminar(id);
        if (cultivoEliminado != null) {
            return ResponseEntity.ok(cultivoEliminado);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<Cultivo> restaurar(@PathVariable Long id) {
        Cultivo cultivoRestaurado = cultivoService.restaurar(id);
        if (cultivoRestaurado != null) {
            return ResponseEntity.ok(cultivoRestaurado);
        }
        return ResponseEntity.notFound().build();
    }
}
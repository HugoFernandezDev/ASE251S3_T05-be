package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.model.Cultivo;
import Agropacayales.valleGrande.service.CultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return cultivoService.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cultivo> crear(@RequestBody Cultivo cultivo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cultivoService.crear(cultivo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cultivo> editar(@PathVariable Long id, @RequestBody Cultivo cultivo) {
        Cultivo editado = cultivoService.editar(id, cultivo);
        return (editado != null) ? ResponseEntity.ok(editado) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<Cultivo> eliminar(@PathVariable Long id) {
        Cultivo eliminado = cultivoService.eliminar(id);
        return (eliminado != null) ? ResponseEntity.ok(eliminado) : ResponseEntity.notFound().build();
    }
}
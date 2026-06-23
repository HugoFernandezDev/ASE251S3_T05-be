package Agropacayales.valleGrande.rest;

import Agropacayales.valleGrande.dto.request.HarvestRequestDTO;
import Agropacayales.valleGrande.dto.response.HarvestResponseDTO;
import Agropacayales.valleGrande.service.HarvestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/harvest")
@CrossOrigin
public class HarvestController {

    private final HarvestService harvestService;

    public HarvestController(HarvestService harvestService) {
        this.harvestService = harvestService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> crearCosecha(@RequestBody HarvestRequestDTO requestDTO) {
        try {
            HarvestResponseDTO nuevaCosecha = harvestService.registrarCosechaTransaccional(requestDTO);
            return new ResponseEntity<>(nuevaCosecha, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<HarvestResponseDTO>> listarCosechas() {
        return ResponseEntity.ok(harvestService.listarTodas());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<HarvestResponseDTO> obtenerCosecha(@PathVariable Integer id) {
        return ResponseEntity.ok(harvestService.obtenerPorId(id));
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<String> darDeBajaCosecha(@PathVariable Integer id) {
        try {
            harvestService.eliminarLogico(id);
            return ResponseEntity.ok("Cosecha dada de baja de manera lógica correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
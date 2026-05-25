package Agropacayales.valleGrande.service.impl;

import Agropacayales.valleGrande.exception.BusinessValidationException;
import Agropacayales.valleGrande.model.Insumo;
import Agropacayales.valleGrande.repository.InsumoRepository;
import Agropacayales.valleGrande.service.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InsumoServiceImpl implements InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;

    private static final List<String> TIPOS_PERMITIDOS = List.of(
        "FERTILIZANTE", "PESTICIDA", "HERBICIDA", "FUNGICIDA", "SEMILLA", "OTRO"
    );

    private static final List<String> UNIDADES_PERMITIDAS = List.of(
        "kg", "g", "L", "ml", "unidad", "bolsa", "saco", "bidon", "tonelada"
    );

    @Override
    public List<Insumo> listarTodos() {
        return insumoRepository.findAll();
    }

    @Override
    public Optional<Insumo> listarPorId(Long id) {
        return insumoRepository.findById(id);
    }

    @Override
    public List<Insumo> listarPorEstado(Boolean estado) {
        return insumoRepository.findByEstado(estado);
    }

    @Override
    public Insumo crear(Insumo insumo) {
        validarDatosInsumo(insumo, null);
        LocalDateTime now = LocalDateTime.now();
        insumo.setEstado(true);
        insumo.setCreatedAt(now);
        insumo.setUpdatedAt(null);
        insumo.setDeletedAt(null);
        insumo.setRestoredAt(null);
        return insumoRepository.save(insumo);
    }

    @Override
    public Insumo editar(Long id, Insumo insumo) {
        Optional<Insumo> existente = insumoRepository.findById(id);
        if (existente.isPresent()) {
            Insumo insumoActualizar = existente.get();

            // Bloquear edición sobre registros soft-deleted/inactivos
            if (!Boolean.TRUE.equals(insumoActualizar.getEstado())) {
                throw new BusinessValidationException(
                    "No se puede editar el insumo '" + insumoActualizar.getNombre() + 
                    "' porque se encuentra inactivo/eliminado. Por favor, restaure el insumo primero."
                );
            }

            validarDatosInsumo(insumo, id);

            insumoActualizar.setUpdatedAt(LocalDateTime.now());
            insumoActualizar.setNombre(insumo.getNombre());
            insumoActualizar.setDescripcion(insumo.getDescripcion());
            insumoActualizar.setPrecio(insumo.getPrecio());
            insumoActualizar.setStock(insumo.getStock());
            insumoActualizar.setUnidadMedida(insumo.getUnidadMedida());
            insumoActualizar.setTipoInsumo(insumo.getTipoInsumo());
            insumoActualizar.setProveedor(insumo.getProveedor());
            insumoActualizar.setPresentacion(insumo.getPresentacion());
            return insumoRepository.save(insumoActualizar);
        }
        return null;
    }

    @Override
    public Insumo eliminar(Long id) {
        Optional<Insumo> existente = insumoRepository.findById(id);
        if (existente.isPresent()) {
            Insumo insumo = existente.get();

            // Evitar archivar/soft-delete si hay inventario remanente activo
            if (insumo.getStock() != null && insumo.getStock() > 0) {
                throw new BusinessValidationException(
                    "No se puede eliminar/archivar el insumo '" + insumo.getNombre() + 
                    "' porque todavia cuenta con " + insumo.getStock() + " " + insumo.getUnidadMedida() + 
                    " disponibles en el inventario activo."
                );
            }

            insumo.setEstado(false);
            insumo.setDeletedAt(LocalDateTime.now());
            return insumoRepository.save(insumo);
        }
        return null;
    }

    @Override
    public Insumo restaurar(Long id) {
        Optional<Insumo> existente = insumoRepository.findById(id);
        if (existente.isPresent()) {
            Insumo insumo = existente.get();
            insumo.setEstado(true);
            insumo.setRestoredAt(LocalDateTime.now());
            return insumoRepository.save(insumo);
        }
        return null;
    }

    // Método centralizado de validación de negocio e integración
    private void validarDatosInsumo(Insumo insumo, Long idExcluir) {
        // 1. Normalizar y validar Unidad de Medida (Agraria)
        if (insumo.getUnidadMedida() == null || insumo.getUnidadMedida().isBlank()) {
            throw new BusinessValidationException("La unidad de medida es obligatoria.");
        }

        String unidadTrimmed = insumo.getUnidadMedida().trim();
        String unidadNormalizada = null;
        for (String unidad : UNIDADES_PERMITIDAS) {
            if (unidad.equalsIgnoreCase(unidadTrimmed)) {
                unidadNormalizada = unidad;
                break;
            }
        }

        if (unidadNormalizada == null) {
            throw new BusinessValidationException(
                "La unidad de medida '" + unidadTrimmed + "' no es valida. " +
                "Unidades aceptadas: " + String.join(", ", UNIDADES_PERMITIDAS)
            );
        }
        insumo.setUnidadMedida(unidadNormalizada); // Guarda la unidad normalizada

        // 2. Normalizar y validar Categorías de Insumo
        if (insumo.getTipoInsumo() == null || insumo.getTipoInsumo().isBlank()) {
            throw new BusinessValidationException("El tipo de insumo es obligatorio.");
        }

        String tipoUpper = insumo.getTipoInsumo().trim().toUpperCase();
        if (!TIPOS_PERMITIDOS.contains(tipoUpper)) {
            throw new BusinessValidationException(
                "El tipo de insumo '" + insumo.getTipoInsumo() + "' no es valido. " +
                "Tipos aceptados: " + String.join(", ", TIPOS_PERMITIDOS)
            );
        }
        insumo.setTipoInsumo(tipoUpper); // Guarda estandarizado en mayúsculas

        // 3. Validar Unicidad del Nombre en registros activos
        if (insumo.getNombre() == null || insumo.getNombre().isBlank()) {
            throw new BusinessValidationException("El nombre del insumo es obligatorio.");
        }

        String nombreTrimmed = insumo.getNombre().trim();
        insumo.setNombre(nombreTrimmed);

        boolean existeDuplicado;
        if (idExcluir == null) {
            existeDuplicado = insumoRepository.existsByNombreIgnoreCaseAndEstadoTrue(nombreTrimmed);
        } else {
            existeDuplicado = insumoRepository.existsByNombreIgnoreCaseAndEstadoTrueAndIdInsumoNot(nombreTrimmed, idExcluir);
        }

        if (existeDuplicado) {
            throw new BusinessValidationException(
                "Ya existe un insumo activo registrado con el nombre '" + nombreTrimmed + "'."
            );
        }

        // 4. Límites de seguridad preventivos (Anti-Fat Finger)
        if (insumo.getStock() != null && insumo.getStock() > 1000000) {
            throw new BusinessValidationException(
                "El stock ingresado (" + insumo.getStock() + ") supera el limite maximo permitido en almacen (1,000,000 unidades)."
            );
        }
        if (insumo.getPrecio() != null && insumo.getPrecio().doubleValue() > 50000.0) {
            throw new BusinessValidationException(
                "El precio ingresado ($" + insumo.getPrecio() + ") supera el precio unitario maximo razonable ($50,000.00)."
            );
        }
    }
}

package Agropacayales.valleGrande.repository;

import Agropacayales.valleGrande.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

    List<Insumo> findByEstado(Boolean estado);

    // Validar si existe otro insumo activo con el mismo nombre (ignora mayúsculas/minúsculas)
    boolean existsByNombreIgnoreCaseAndEstadoTrue(String nombre);

    // Validar si existe otro insumo activo con el mismo nombre, excluyendo el ID actual (para la edición)
    boolean existsByNombreIgnoreCaseAndEstadoTrueAndIdInsumoNot(String nombre, Long idInsumo);

    // Buscar por coincidencia de nombre parcial (insensible a mayúsculas)
    List<Insumo> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por tipo de insumo (insensible a mayúsculas)
    List<Insumo> findByTipoInsumoIgnoreCase(String tipoInsumo);
}

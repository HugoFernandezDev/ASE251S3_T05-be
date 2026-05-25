package Agropacayales.valleGrande.repository;

import Agropacayales.valleGrande.model.ActividadCultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActividadCultivoRepository extends JpaRepository<ActividadCultivo, Long> {
    List<ActividadCultivo> findByEstado(Boolean estado);
}

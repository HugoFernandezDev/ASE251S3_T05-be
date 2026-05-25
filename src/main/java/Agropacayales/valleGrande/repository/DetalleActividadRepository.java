package Agropacayales.valleGrande.repository;

import Agropacayales.valleGrande.model.DetalleActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleActividadRepository extends JpaRepository<DetalleActividad, Long> {
}

package Agropacayales.valleGrande.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Agropacayales.valleGrande.model.Harvest;
import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Integer> {
    List<Harvest> findByEstadoTrue();
}
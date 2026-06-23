package Agropacayales.valleGrande.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "harvest")
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_harvest")
    private Integer idHarvest;

    @Column(name = "responsable", nullable = false, length = 100)
    private String responsable;

    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HarvestPlantingCycle> detalles = new ArrayList<>();

    public void addDetalle(HarvestPlantingCycle detalle) {
        detalles.add(detalle);
        detalle.setHarvest(this);
    }

    // Getters y Setters
    public Integer getIdHarvest() { return idHarvest; }
    public void setIdHarvest(Integer idHarvest) { this.idHarvest = idHarvest; }
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public LocalDate getFechaCosecha() { return fechaCosecha; }
    public void setFechaCosecha(LocalDate fechaCosecha) { this.fechaCosecha = fechaCosecha; }
    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<HarvestPlantingCycle> getDetalles() { return detalles; }
    public void setDetalles(List<HarvestPlantingCycle> detalles) { this.detalles = detalles; }
}
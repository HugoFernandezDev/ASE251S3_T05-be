package Agropacayales.valleGrande.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HarvestResponseDTO {
    private Integer idHarvest; 
    private String responsable;
    private LocalDate fechaCosecha;
    private Boolean estado;
    private LocalDateTime createdAt;
    private List<DetalleResponseDTO> detalles;

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
    public List<DetalleResponseDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleResponseDTO> detalles) { this.detalles = detalles; }
}
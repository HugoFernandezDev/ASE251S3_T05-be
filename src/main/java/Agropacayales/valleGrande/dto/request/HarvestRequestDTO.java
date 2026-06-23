package Agropacayales.valleGrande.dto.request;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat; 

public class HarvestRequestDTO {
    private String responsable;
    
   
    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate fechaCosecha;
    
    private List<DetalleCosechaDTO> detalles;

    // Getters y Setters
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public LocalDate getFechaCosecha() { return fechaCosecha; }
    public void setFechaCosecha(LocalDate fechaCosecha) { this.fechaCosecha = fechaCosecha; }
    public List<DetalleCosechaDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCosechaDTO> detalles) { this.detalles = detalles; }
}
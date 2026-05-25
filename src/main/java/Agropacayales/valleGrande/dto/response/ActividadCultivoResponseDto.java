package Agropacayales.valleGrande.dto.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ActividadCultivoResponseDto {
    private Long idActividad;
    private Long idCultivo;
    private String nombreCultivo;
    private String tipoActividad;
    private String descripcion;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    private LocalDateTime fechaActividad;
    
    private BigDecimal costoTotal;
    private Boolean estado;
    private List<DetalleActividadResponseDto> detalles;
}

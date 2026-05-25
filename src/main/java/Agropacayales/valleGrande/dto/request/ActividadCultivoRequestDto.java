package Agropacayales.valleGrande.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class ActividadCultivoRequestDto {
    private Long idCultivo;
    private String tipoActividad;
    private String descripcion;
    private List<DetalleActividadRequestDto> detalles;
}

package Agropacayales.valleGrande.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleActividadResponseDto {
    private Long idDetalle;
    private Long idInsumo;
    private String nombreInsumo;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}

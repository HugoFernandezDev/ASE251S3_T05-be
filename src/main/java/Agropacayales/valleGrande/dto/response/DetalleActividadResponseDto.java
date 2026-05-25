package Agropacayales.valleGrande.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleActividadResponseDto {
    private Long idDetalle;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}

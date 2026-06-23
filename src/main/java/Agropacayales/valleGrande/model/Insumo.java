package Agropacayales.valleGrande.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Long idInsumo;

    @NotBlank(message = "El nombre del insumo no puede estar vacio.")
    @Size(max = 100, message = "El nombre del insumo no puede superar los 100 caracteres.")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 1000, message = "La descripcion no puede superar los 1000 caracteres.")
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio del insumo debe ser un numero positivo mayor que cero.")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio.")
    @PositiveOrZero(message = "El stock disponible no puede ser un numero negativo.")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @NotBlank(message = "La unidad de medida es obligatoria.")
    @Size(max = 20, message = "La unidad de medida no puede superar los 20 caracteres.")
    @Column(name = "unidad_medida", length = 20)
    private String unidadMedida;

    @NotBlank(message = "El tipo de insumo es obligatorio.")
    @Size(max = 50, message = "El tipo de insumo no puede superar los 50 caracteres.")
    @Column(name = "tipo_insumo", length = 50)
    private String tipoInsumo;

    @Size(max = 100, message = "El proveedor no puede superar los 100 caracteres.")
    @Column(name = "proveedor", length = 100)
    private String proveedor;

    @Size(max = 100, message = "La presentacion no puede superar los 100 caracteres.")
    @Column(name = "presentacion", length = 100)
    private String presentacion;

    @Column(name = "estado")
    private Boolean estado = true;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "restored_at")
    private LocalDateTime restoredAt;
}

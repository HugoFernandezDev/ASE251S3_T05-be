package Agropacayales.valleGrande.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actividad_cultivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActividadCultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long idActividad;

    @ManyToOne
    @JoinColumn(name = "id_cultivo", nullable = false)
    private Cultivo cultivo;

    @Column(name = "tipo_actividad", nullable = false, length = 50)
    private String tipoActividad;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "fecha_actividad", nullable = false)
    private LocalDateTime fechaActividad;

    @Column(name = "costo_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotal;

    @Column(name = "estado")
    private Boolean estado = true;

    // CAMPOS DE AUDITORÍA
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

    @OneToMany(mappedBy = "actividadCultivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleActividad> detalles = new ArrayList<>();
}

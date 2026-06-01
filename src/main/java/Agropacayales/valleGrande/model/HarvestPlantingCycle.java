package Agropacayales.valleGrande.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "harvest_planting_cycle")
public class HarvestPlantingCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_harvest_detail")
    private Integer idHarvestDetail;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_harvest", nullable = false)
    private Harvest harvest;

    @Column(name = "id_cultivo", nullable = false)
    private Integer idCultivo;

    @Column(name = "kilos_optimos", nullable = false, precision = 10, scale = 2)
    private BigDecimal kilosOptimos;

    @Column(name = "kilos_merma", nullable = false, precision = 10, scale = 2)
    private BigDecimal kilosMerma;

    // Getters y Setters
    public Integer getIdHarvestDetail() { return idHarvestDetail; }
    public void setIdHarvestDetail(Integer idHarvestDetail) { this.idHarvestDetail = idHarvestDetail; }
    public Harvest getHarvest() { return harvest; }
    public void setHarvest(Harvest harvest) { this.harvest = harvest; }
    public Integer getIdCultivo() { return idCultivo; }
    public void setIdCultivo(Integer idCultivo) { this.idCultivo = idCultivo; }
    public BigDecimal getKilosOptimos() { return kilosOptimos; }
    public void setKilosOptimos(BigDecimal kilosOptimos) { this.kilosOptimos = kilosOptimos; }
    public BigDecimal getKilosMerma() { return kilosMerma; }
    public void setKilosMerma(BigDecimal kilosMerma) { this.kilosMerma = kilosMerma; }
}
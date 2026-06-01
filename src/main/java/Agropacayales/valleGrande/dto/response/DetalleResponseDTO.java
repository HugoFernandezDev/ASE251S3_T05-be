package Agropacayales.valleGrande.dto.response;

import java.math.BigDecimal;

public class DetalleResponseDTO {
    private Integer idHarvestDetail;
    private Integer idCultivo;
    private BigDecimal kilosOptimos;
    private BigDecimal kilosMerma;
    private BigDecimal totalKilos;

    // Getters y Setters
    public Integer getIdHarvestDetail() { return idHarvestDetail; }
    public void setIdHarvestDetail(Integer idHarvestDetail) { this.idHarvestDetail = idHarvestDetail; }
    public Integer getIdCultivo() { return idCultivo; }
    public void setIdCultivo(Integer idCultivo) { this.idCultivo = idCultivo; }
    public BigDecimal getKilosOptimos() { return kilosOptimos; }
    public void setKilosOptimos(BigDecimal kilosOptimos) { this.kilosOptimos = kilosOptimos; }
    public BigDecimal getKilosMerma() { return kilosMerma; }
    public void setKilosMerma(BigDecimal kilosMerma) { this.kilosMerma = kilosMerma; }
    public BigDecimal getTotalKilos() { return totalKilos; }
    public void setTotalKilos(BigDecimal totalKilos) { this.totalKilos = totalKilos; }
}
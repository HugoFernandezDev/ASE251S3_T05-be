package Agropacayales.valleGrande.dto.request;

import java.math.BigDecimal;

public class DetalleCosechaDTO {
    private Integer idCultivo;
    private BigDecimal kilosOptimos;
    private BigDecimal kilosMerma;

    // Getters y Setters
    public Integer getIdCultivo() { return idCultivo; }
    public void setIdCultivo(Integer idCultivo) { this.idCultivo = idCultivo; }
    public BigDecimal getKilosOptimos() { return kilosOptimos; }
    public void setKilosOptimos(BigDecimal kilosOptimos) { this.kilosOptimos = kilosOptimos; }
    public BigDecimal getKilosMerma() { return kilosMerma; }
    public void setKilosMerma(BigDecimal kilosMerma) { this.kilosMerma = kilosMerma; }
}
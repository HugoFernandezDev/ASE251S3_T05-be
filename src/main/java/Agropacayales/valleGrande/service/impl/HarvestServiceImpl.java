package Agropacayales.valleGrande.service.impl;

import Agropacayales.valleGrande.dto.request.HarvestRequestDTO;
import Agropacayales.valleGrande.dto.request.DetalleCosechaDTO;
import Agropacayales.valleGrande.dto.response.HarvestResponseDTO;
import Agropacayales.valleGrande.dto.response.DetalleResponseDTO;
import Agropacayales.valleGrande.model.Harvest;
import Agropacayales.valleGrande.model.HarvestPlantingCycle;
import Agropacayales.valleGrande.repository.HarvestRepository;
import Agropacayales.valleGrande.service.HarvestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository) {
        this.harvestRepository = harvestRepository;
    }

    @Override
    @Transactional
    public HarvestResponseDTO registrarCosechaTransaccional(HarvestRequestDTO dto) {
        Harvest harvest = new Harvest();
        harvest.setResponsable(dto.getResponsable());
        harvest.setFechaCosecha(dto.getFechaCosecha());
        harvest.setCreatedAt(LocalDateTime.now());
        harvest.setEstado(true);

        for (DetalleCosechaDTO detalleDto : dto.getDetalles()) {
            BigDecimal optimos = detalleDto.getKilosOptimos();
            BigDecimal merma = detalleDto.getKilosMerma();
            BigDecimal total = optimos.add(merma);

            if (total.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal porcentajeMerma = merma.divide(total, 4, RoundingMode.HALF_UP);
                if (porcentajeMerma.compareTo(new BigDecimal("0.20")) > 0) {
                    throw new RuntimeException("Error Transaccional: El cultivo ID " + detalleDto.getIdCultivo() 
                            + " excede el límite crítico de merma tolerada (20%). Operación abortada.");
                }
            }

            HarvestPlantingCycle detalleEntity = new HarvestPlantingCycle();
            detalleEntity.setIdCultivo(detalleDto.getIdCultivo());
            detalleEntity.setKilosOptimos(optimos);
            detalleEntity.setKilosMerma(merma);

            harvest.addDetalle(detalleEntity);
        }

        Harvest guardado = harvestRepository.save(harvest);
        return convertirAResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestResponseDTO> listarTodas() {
        return harvestRepository.findByEstadoTrue().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HarvestResponseDTO obtenerPorId(Integer id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La cosecha buscada no existe en el sistema."));
        return convertirAResponseDTO(harvest);
    }

    @Override
    @Transactional
    public void eliminarLogico(Integer id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La cosecha buscada no existe en el sistema."));
        harvest.setEstado(false);
        harvest.setUpdatedAt(LocalDateTime.now());
        harvestRepository.save(harvest);
    }

    // Método auxiliar de mapeo de Entidad -> Response DTO
    private HarvestResponseDTO convertirAResponseDTO(Harvest entity) {
        HarvestResponseDTO res = new HarvestResponseDTO();
        res.setIdHarvest(entity.getIdHarvest());
        res.setResponsable(entity.getResponsable());
        res.setFechaCosecha(entity.getFechaCosecha());
        res.setEstado(entity.getEstado());
        res.setCreatedAt(entity.getCreatedAt());

        if (entity.getDetalles() != null) {
            List<DetalleResponseDTO> detallesDto = entity.getDetalles().stream().map(d -> {
                DetalleResponseDTO dDto = new DetalleResponseDTO();
                dDto.setIdHarvestDetail(d.getIdHarvestDetail());
                dDto.setIdCultivo(d.getIdCultivo());
                dDto.setKilosOptimos(d.getKilosOptimos());
                dDto.setKilosMerma(d.getKilosMerma());
                // En el modelo se asume que totalKilos mapea la columna calculada de la BD
                dDto.setTotalKilos(d.getKilosOptimos().add(d.getKilosMerma())); 
                return dDto;
            }).collect(Collectors.toList());
            res.setDetalles(detallesDto);
        }
        return res;
    }
}
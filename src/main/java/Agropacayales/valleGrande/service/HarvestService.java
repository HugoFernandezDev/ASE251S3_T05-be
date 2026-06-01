package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.dto.request.HarvestRequestDTO;
import Agropacayales.valleGrande.dto.response.HarvestResponseDTO;
import java.util.List;

public interface HarvestService {
    HarvestResponseDTO registrarCosechaTransaccional(HarvestRequestDTO dto);
    List<HarvestResponseDTO> listarTodas();
    HarvestResponseDTO obtenerPorId(Integer id);
    void eliminarLogico(Integer id);
}
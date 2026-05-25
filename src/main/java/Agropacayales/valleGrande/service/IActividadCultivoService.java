package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.dto.request.ActividadCultivoRequestDto;
import Agropacayales.valleGrande.dto.response.ActividadCultivoResponseDto;
import java.util.List;

public interface IActividadCultivoService {
    ActividadCultivoResponseDto registrarActividad(ActividadCultivoRequestDto request);
    List<ActividadCultivoResponseDto> listarTodas();
    List<ActividadCultivoResponseDto> listarPorEstado(Boolean estado);
    ActividadCultivoResponseDto buscarPorId(Long id);
    ActividadCultivoResponseDto eliminar(Long id);
    ActividadCultivoResponseDto restaurar(Long id);
}

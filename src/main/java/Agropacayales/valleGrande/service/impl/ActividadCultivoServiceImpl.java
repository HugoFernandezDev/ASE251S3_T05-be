package Agropacayales.valleGrande.service.impl;

import Agropacayales.valleGrande.dto.request.ActividadCultivoRequestDto;
import Agropacayales.valleGrande.dto.request.DetalleActividadRequestDto;
import Agropacayales.valleGrande.dto.response.ActividadCultivoResponseDto;
import Agropacayales.valleGrande.dto.response.DetalleActividadResponseDto;
import Agropacayales.valleGrande.model.ActividadCultivo;
import Agropacayales.valleGrande.model.Cultivo;
import Agropacayales.valleGrande.model.DetalleActividad;
import Agropacayales.valleGrande.model.Insumo;
import Agropacayales.valleGrande.repository.ActividadCultivoRepository;
import Agropacayales.valleGrande.repository.CultivoRepository;
import Agropacayales.valleGrande.repository.InsumoRepository;
import Agropacayales.valleGrande.service.IActividadCultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadCultivoServiceImpl implements IActividadCultivoService {

    @Autowired
    private ActividadCultivoRepository actividadRepository;

    @Autowired
    private CultivoRepository cultivoRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Override
    @Transactional
    public ActividadCultivoResponseDto registrarActividad(ActividadCultivoRequestDto request) {
        // 1. Validar Cultivo
        Cultivo cultivo = cultivoRepository.findById(request.getIdCultivo())
                .orElseThrow(() -> new RuntimeException("Cultivo no encontrado con ID: " + request.getIdCultivo()));

        if (!Boolean.TRUE.equals(cultivo.getEstado())) {
            throw new RuntimeException("El cultivo seleccionado está inactivo.");
        }

        // 2. Crear cabecera de la Actividad
        ActividadCultivo actividad = new ActividadCultivo();
        actividad.setCultivo(cultivo);
        actividad.setTipoActividad(request.getTipoActividad());
        actividad.setDescripcion(request.getDescripcion());
        actividad.setFechaActividad(LocalDateTime.now());
        actividad.setEstado(true);
        actividad.setCreatedAt(LocalDateTime.now());

        BigDecimal costoTotal = BigDecimal.ZERO;
        List<DetalleActividad> detalles = new ArrayList<>();

        // 3. Procesar detalles (si existen)
        if (request.getDetalles() != null && !request.getDetalles().isEmpty()) {
            for (DetalleActividadRequestDto detDto : request.getDetalles()) {
                // Validar Insumo
                Insumo insumo = insumoRepository.findById(detDto.getIdInsumo())
                        .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + detDto.getIdInsumo()));

                if (!Boolean.TRUE.equals(insumo.getEstado())) {
                    throw new RuntimeException("El insumo '" + insumo.getNombre() + "' está inactivo.");
                }

                // Validar Stock
                if (insumo.getStock() < detDto.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el insumo '" + insumo.getNombre() + 
                            "'. Stock disponible: " + insumo.getStock() + ", solicitado: " + detDto.getCantidad());
                }

                // Restar Stock
                insumo.setStock(insumo.getStock() - detDto.getCantidad());
                insumoRepository.save(insumo);

                // Calcular costos
                BigDecimal precioUnitario = insumo.getPrecio();
                BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(detDto.getCantidad()));
                costoTotal = costoTotal.add(subtotal);

                // Crear Detalle de Actividad
                DetalleActividad detalle = new DetalleActividad();
                detalle.setActividadCultivo(actividad);
                detalle.setInsumo(insumo);
                detalle.setCantidad(detDto.getCantidad());
                detalle.setPrecioUnitario(precioUnitario);
                detalle.setSubtotal(subtotal);

                detalles.add(detalle);
            }
        }

        actividad.setCostoTotal(costoTotal);
        actividad.setDetalles(detalles);

        // Guardar transacción (Cabecera y Detalle en cascada)
        ActividadCultivo guardada = actividadRepository.save(actividad);

        return convertToResponseDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadCultivoResponseDto> listarTodas() {
        return actividadRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadCultivoResponseDto> listarPorEstado(Boolean estado) {
        return actividadRepository.findByEstado(estado).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ActividadCultivoResponseDto buscarPorId(Long id) {
        ActividadCultivo actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad de cultivo no encontrada con ID: " + id));
        return convertToResponseDto(actividad);
    }

    @Override
    @Transactional
    public ActividadCultivoResponseDto eliminar(Long id) {
        ActividadCultivo actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad de cultivo no encontrada con ID: " + id));
        actividad.setEstado(false);
        actividad.setDeletedAt(LocalDateTime.now());
        ActividadCultivo guardada = actividadRepository.save(actividad);
        return convertToResponseDto(guardada);
    }

    @Override
    @Transactional
    public ActividadCultivoResponseDto restaurar(Long id) {
        ActividadCultivo actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad de cultivo no encontrada con ID: " + id));
        actividad.setEstado(true);
        actividad.setRestoredAt(LocalDateTime.now());
        ActividadCultivo guardada = actividadRepository.save(actividad);
        return convertToResponseDto(guardada);
    }

    // Método auxiliar para mapear Entidad -> DTO de respuesta
    private ActividadCultivoResponseDto convertToResponseDto(ActividadCultivo entity) {
        ActividadCultivoResponseDto dto = new ActividadCultivoResponseDto();
        dto.setIdActividad(entity.getIdActividad());
        dto.setIdCultivo(entity.getCultivo().getIdCultivo());
        dto.setNombreCultivo(entity.getCultivo().getNombre());
        dto.setTipoActividad(entity.getTipoActividad());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaActividad(entity.getFechaActividad());
        dto.setCostoTotal(entity.getCostoTotal());
        dto.setEstado(entity.getEstado());

        List<DetalleActividadResponseDto> detalleDtos = new ArrayList<>();
        if (entity.getDetalles() != null) {
            for (DetalleActividad det : entity.getDetalles()) {
                DetalleActividadResponseDto dDto = new DetalleActividadResponseDto();
                dDto.setIdDetalle(det.getIdDetalle());
                dDto.setIdInsumo(det.getInsumo().getIdInsumo());
                dDto.setNombreInsumo(det.getInsumo().getNombre());
                dDto.setCantidad(det.getCantidad());
                dDto.setPrecioUnitario(det.getPrecioUnitario());
                dDto.setSubtotal(det.getSubtotal());
                detalleDtos.add(dDto);
            }
        }
        dto.setDetalles(detalleDtos);
        return dto;
    }
}

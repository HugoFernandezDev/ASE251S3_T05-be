package Agropacayales.valleGrande.service.impl;

import Agropacayales.valleGrande.model.Insumo;
import Agropacayales.valleGrande.repository.InsumoRepository;
import Agropacayales.valleGrande.service.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InsumoServiceImpl implements InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;

    @Override
    public List<Insumo> listarTodos() {
        return insumoRepository.findAll();
    }

    @Override
    public Optional<Insumo> listarPorId(Long id) {
        return insumoRepository.findById(id);
    }

    @Override
    public List<Insumo> listarPorEstado(Boolean estado) {
        return insumoRepository.findByEstado(estado);
    }

    @Override
    public Insumo crear(Insumo insumo) {
        LocalDateTime now = LocalDateTime.now();
        insumo.setEstado(true);
        insumo.setCreatedAt(now);
        insumo.setUpdatedAt(null);
        insumo.setDeletedAt(null);
        insumo.setRestoredAt(null);
        return insumoRepository.save(insumo);
    }

    @Override
    public Insumo editar(Long id, Insumo insumo) {
        Optional<Insumo> existente = insumoRepository.findById(id);
        if (existente.isPresent()) {
            Insumo insumoActualizar = existente.get();
            insumoActualizar.setUpdatedAt(LocalDateTime.now());
            insumoActualizar.setNombre(insumo.getNombre());
            insumoActualizar.setDescripcion(insumo.getDescripcion());
            insumoActualizar.setPrecio(insumo.getPrecio());
            insumoActualizar.setStock(insumo.getStock());
            insumoActualizar.setUnidadMedida(insumo.getUnidadMedida());
            insumoActualizar.setTipoInsumo(insumo.getTipoInsumo());
            insumoActualizar.setProveedor(insumo.getProveedor());
            insumoActualizar.setPresentacion(insumo.getPresentacion());
            return insumoRepository.save(insumoActualizar);
        }
        return null;
    }

    @Override
    public Insumo eliminar(Long id) {
        Optional<Insumo> existente = insumoRepository.findById(id);
        if (existente.isPresent()) {
            Insumo insumo = existente.get();
            insumo.setEstado(false);
            insumo.setDeletedAt(LocalDateTime.now());
            return insumoRepository.save(insumo);
        }
        return null;
    }

    @Override
    public Insumo restaurar(Long id) {
        Optional<Insumo> existente = insumoRepository.findById(id);
        if (existente.isPresent()) {
            Insumo insumo = existente.get();
            insumo.setEstado(true);
            insumo.setRestoredAt(LocalDateTime.now());
            return insumoRepository.save(insumo);
        }
        return null;
    }
}

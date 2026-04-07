package Agropacayales.valleGrande.service.impl;

import Agropacayales.valleGrande.model.Cultivo;
import Agropacayales.valleGrande.repository.CultivoRepository;
import Agropacayales.valleGrande.service.CultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CultivoServiceImpl implements CultivoService {

    @Autowired
    private CultivoRepository cultivoRepository;

    @Override
    public List<Cultivo> listarTodos() {
        return cultivoRepository.findAll();
    }

    @Override
    public Optional<Cultivo> listarPorId(Long id) {
        return cultivoRepository.findById(id);
    }

    @Override
    public List<Cultivo> listarPorEstado(Boolean estado) {
        return cultivoRepository.findByEstado(estado);
    }

    @Override
    public Cultivo crear(Cultivo cultivo) {
        validarCultivo(cultivo);
        cultivo.setEstado(true);
        return cultivoRepository.save(cultivo);
    }

    @Override
    public Cultivo editar(Long id, Cultivo cultivo) {
        Optional<Cultivo> existente = cultivoRepository.findById(id);
        if (existente.isPresent()) {
            validarCultivo(cultivo);
            Cultivo cultivoActualizar = existente.get();
            cultivoActualizar.setNombre(cultivo.getNombre());
            cultivoActualizar.setTipoCultivo(cultivo.getTipoCultivo());
            cultivoActualizar.setFrecuenciaRiegoDias(cultivo.getFrecuenciaRiegoDias());
            cultivoActualizar.setTemperaturaIdeal(cultivo.getTemperaturaIdeal());
            cultivoActualizar.setFechaSiembra(cultivo.getFechaSiembra());
            cultivoActualizar.setRequiereSombra(cultivo.getRequiereSombra());
            cultivoActualizar.setObservaciones(cultivo.getObservaciones());
            return cultivoRepository.save(cultivoActualizar);
        }
        return null;
    }

    @Override
    public Cultivo eliminar(Long id) {
        Optional<Cultivo> existente = cultivoRepository.findById(id);
        if (existente.isPresent()) {
            Cultivo cultivo = existente.get();
            cultivo.setEstado(false);
            return cultivoRepository.save(cultivo);
        }
        return null;
    }

    @Override
    public Cultivo restaurar(Long id) {
        Optional<Cultivo> existente = cultivoRepository.findById(id);
        if (existente.isPresent()) {
            Cultivo cultivo = existente.get();
            cultivo.setEstado(true);
            return cultivoRepository.save(cultivo);
        }
        return null;
    }

    private void validarCultivo(Cultivo cultivo) {
        if (cultivo.getFrecuenciaRiegoDias() == null || cultivo.getFrecuenciaRiegoDias() <= 0) {
            throw new IllegalArgumentException("La frecuencia de riego debe ser mayor que cero.");
        }

        if (cultivo.getTemperaturaIdeal() == null) {
            throw new IllegalArgumentException("La temperatura ideal es obligatoria.");
        }

        if (cultivo.getNombre() == null || cultivo.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cultivo es obligatorio.");
        }

        if (cultivo.getTipoCultivo() == null || cultivo.getTipoCultivo().isBlank()) {
            throw new IllegalArgumentException("El tipo de cultivo es obligatorio.");
        }
    }
}
package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.model.Cultivo;

import java.util.List;
import java.util.Optional;

public interface CultivoService {

    List<Cultivo> listarTodos();

    Optional<Cultivo> listarPorId(Long id);

    List<Cultivo> listarPorEstado(Boolean estado);

    Cultivo crear(Cultivo cultivo);

    Cultivo editar(Long id, Cultivo cultivo);

    Cultivo eliminar(Long id);

    Cultivo restaurar(Long id);
}
package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.model.Insumo;

import java.util.List;
import java.util.Optional;

public interface InsumoService {

    // Listar todos los insumos
    List<Insumo> listarTodos();

    // Listar insumo por ID
    Optional<Insumo> listarPorId(Long id);

    // Listar insumos por estado (activos/inactivos)
    List<Insumo> listarPorEstado(Boolean estado);

    // Crear nuevo insumo
    Insumo crear(Insumo insumo);

    // Editar insumo existente
    Insumo editar(Long id, Insumo insumo);

    // Eliminar lógico (cambiar estado a false)
    Insumo eliminar(Long id);

    // Restaurar lógico (cambiar estado a true)
    Insumo restaurar(Long id);

    // Buscar insumos por nombre (coincidencia parcial, insensible a mayúsculas)
    List<Insumo> buscarPorNombre(String nombre);

    // Filtrar insumos por tipo
    List<Insumo> filtrarPorTipo(String tipo);

    // Listado paginado de insumos
    org.springframework.data.domain.Page<Insumo> listarPaginado(org.springframework.data.domain.Pageable pageable);
}

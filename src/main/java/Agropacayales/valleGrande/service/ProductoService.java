package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    // Listar todos los productos
    List<Producto> listarTodos();

    // Listar producto por ID
    Optional<Producto> listarPorId(Long id);

    // Listar productos por estado (activos/inactivos)
    List<Producto> listarPorEstado(Boolean estado);

    // Crear nuevo producto
    Producto crear(Producto producto);

    // Editar producto existente
    Producto editar(Long id, Producto producto);

    // Eliminar lógico (cambiar estado a false)
    Producto eliminar(Long id);

    // Restaurar lógico (cambiar estado a true)
    Producto restaurar(Long id);

    // Buscar productos por nombre (coincidencia parcial)
    List<Producto> buscarPorNombre(String nombre);

    // Filtrar productos por tipo
    List<Producto> filtrarPorTipo(String tipo);

    // Listar productos con paginación
    Page<Producto> listarPaginado(Pageable pageable);
}

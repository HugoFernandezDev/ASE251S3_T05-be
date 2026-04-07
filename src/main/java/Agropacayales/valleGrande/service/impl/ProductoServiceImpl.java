package Agropacayales.valleGrande.service.impl;

import Agropacayales.valleGrande.model.Producto;
import Agropacayales.valleGrande.repository.ProductoRepository;
import Agropacayales.valleGrande.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> listarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> listarPorEstado(Boolean estado) {
        return productoRepository.findByEstado(estado);
    }

    @Override
    public Producto crear(Producto producto) {
        producto.setEstado(true);
        return productoRepository.save(producto);
    }

    @Override
    public Producto editar(Long id, Producto producto) {
        Optional<Producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            Producto productoActualizar = existente.get();
            productoActualizar.setNombre(producto.getNombre());
            productoActualizar.setDescripcion(producto.getDescripcion());
            productoActualizar.setPrecio(producto.getPrecio());
            productoActualizar.setStock(producto.getStock());
            productoActualizar.setUnidadMedida(producto.getUnidadMedida());
            productoActualizar.setFechaRegistro(producto.getFechaRegistro());
            return productoRepository.save(productoActualizar);
        }
        return null;
    }

    @Override
    public Producto eliminar(Long id) {
        Optional<Producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            Producto producto = existente.get();
            producto.setEstado(false);
            return productoRepository.save(producto);
        }
        return null;
    }

    @Override
    public Producto restaurar(Long id) {
        Optional<Producto> existente = productoRepository.findById(id);
        if (existente.isPresent()) {
            Producto producto = existente.get();
            producto.setEstado(true);
            return productoRepository.save(producto);
        }
        return null;
    }
}
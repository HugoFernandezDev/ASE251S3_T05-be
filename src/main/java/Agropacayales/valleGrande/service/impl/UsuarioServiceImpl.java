package Agropacayales.valleGrande.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Agropacayales.valleGrande.model.Usuario;
import Agropacayales.valleGrande.repository.UsuarioRepository;
import Agropacayales.valleGrande.service.IUsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listarTodos() { 
        return repository.findAll(); 
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) { 
        return repository.findById(id); 
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstado(true);
        return repository.save(usuario);
    }

    @Override
    public Usuario actualizar(Integer id, Usuario datos) {
        return repository.findById(id).map(u -> {
            u.setNombreCompleto(datos.getNombreCompleto());
            u.setUsername(datos.getUsername()); 
            u.setPassword(datos.getPassword());
            u.setCorreo(datos.getCorreo());
            u.setRol(datos.getRol());
            return repository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void eliminarLogico(Integer id) {
        repository.findById(id).ifPresent(u -> {
            u.setEstado(false);
            repository.save(u);
        });
    }

    @Override
    public void restaurarLogico(Integer id) {
        repository.findById(id).ifPresent(u -> {
            u.setEstado(true);
            repository.save(u);
        });
    }
}
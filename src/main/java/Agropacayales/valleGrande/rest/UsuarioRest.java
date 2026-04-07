package Agropacayales.valleGrande.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import Agropacayales.valleGrande.model.Usuario;
import Agropacayales.valleGrande.service.IUsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRest {

    @Autowired
    private IUsuarioService service;

    @GetMapping
    public List<Usuario> listar() { return service.listarTodos(); }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Integer id) { // Cambiado a Integer
        return service.buscarPorId(id).orElse(null); 
    }

    @PutMapping("/{id}")
    public Usuario editar(@PathVariable Integer id, @RequestBody Usuario usuario) { // Cambiado a Integer
        return service.actualizar(id, usuario); 
    }

    @PatchMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) { service.eliminarLogico(id); }

    @PatchMapping("/restaurar/{id}")
    public void restaurar(@PathVariable Integer id) { service.restaurarLogico(id); }
}
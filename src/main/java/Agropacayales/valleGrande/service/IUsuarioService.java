package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarTodos();
    Optional<Usuario> buscarPorId(Integer id); 
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Integer id, Usuario usuario); 
    void eliminarLogico(Integer id); 
    void restaurarLogico(Integer id); 
}
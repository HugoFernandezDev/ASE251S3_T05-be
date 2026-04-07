package Agropacayales.valleGrande.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario; // Sincronizado con INT de SQL

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 100)
    private String correo; 

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String username; 

    @Column(name = "contrasena", nullable = false, length = 255)
    private String password; 

    @Column(length = 20)
    private String rol;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaRegistro; 

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    private Boolean estado;
}
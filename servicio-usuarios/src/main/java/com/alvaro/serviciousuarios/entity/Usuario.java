package com.alvaro.serviciousuarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean enabled;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    /* Los datos de la relación no se cargarán automáticamente cuando se cargue una instancia de la entidad,
     * sino que se cargarán solo cuando se acceda explícitamente a ellos. */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

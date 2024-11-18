package com.ERP.ERP.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@Table(name = "paciente")
public class Paciente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String ci;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String apellidoPaterno;

    @Column(length = 100, nullable = false)
    private String apellidoMaterno;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(length = 50, nullable = false)
    private String genero;

    private String direccion;

    @Column(length = 8, nullable = false)
    @Size(min = 8, max = 8, message = "El número de teléfono debe tener 8 dígitos.")
    private String telefono;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    private Boolean estado;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;
}

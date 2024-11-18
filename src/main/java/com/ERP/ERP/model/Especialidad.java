package com.ERP.ERP.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


@Entity
@Data
@Table(name = "especialidad")
public class Especialidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String descripcion;
}
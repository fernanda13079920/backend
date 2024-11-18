package com.ERP.ERP.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permisos")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String permiso;
}

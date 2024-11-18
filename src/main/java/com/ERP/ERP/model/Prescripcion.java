package com.ERP.ERP.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "prescripcions")
public class Prescripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String indicacion;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_sesion")
    private Sesion sesion;


    @ManyToOne
    @JoinColumn(name = "id_medicamento")
    private Medicamento medicamento;
}


package com.ERP.ERP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "horario")
public class Horario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Temporal(TemporalType.TIME)
    @Column(name = "hora_inicio", nullable = false)
    private Date horaInicio;

    @Temporal(TemporalType.TIME)
    @Column(name = "hora_final", nullable = false)
    private Date horaFinal;

    @Column(name = "duracion_consulta", nullable = false)
    private Integer duracionConsulta; // Duración de cada consulta en minutos

    private Boolean estado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Transient
    public Integer getMaximoFichas() {
        long minutosTotales = (horaFinal.getTime() - horaInicio.getTime()) / (1000 * 60); // Minutos totales
        return (int) (minutosTotales / duracionConsulta); // Número máximo de fichas
    }
}

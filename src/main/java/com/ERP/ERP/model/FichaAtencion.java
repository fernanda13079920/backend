package com.ERP.ERP.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
@Table(name = "ficha_atencion")
public class FichaAtencion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraCierre;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "horafecha_solicitada", nullable = false)
    private Date horafechaSolicitada;

    @Column(name = "estado", nullable = false, length = 20) // Ejemplo: "Pendiente", "Atendida", "Cancelada", "Falta"
    private String estado;

    @Column(name = "asistencia", nullable = false)
    private Boolean asistencia = false; // Marca si asistió o no

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Column(name = "cancelada", nullable = false)
    private Boolean cancelada = false; // Nueva propiedad para marcar cancelaciones
}

package com.ERP.ERP.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "sesions")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraCierre;
    @Column(precision = 10, scale = 1)
    private BigDecimal temperaturaCorporal;
    private int frecuenciaCardiaca;
    private int presionArterial;
    @Column(precision = 10, scale = 1)
    private  BigDecimal pesoCorporal;

    private String observacion;
    private String diagnostico;
    private String documento;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_historial")
    private Historial historial;

    @OneToOne
    @JoinColumn(name = "id_ficha_atencion")
    private FichaAtencion fichaAtencion;
    // @JsonIgnoreProperties("sesion")
    @OneToMany(mappedBy = "sesion", cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Prescripcion> prescripciones;
    // @JsonIgnoreProperties("sesion")
    @OneToMany(mappedBy = "sesion", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<SesionEstudio> sesionEstudios;


}

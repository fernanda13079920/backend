package com.ERP.ERP.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SesionEditarDTO {
    private List<PrescripcionDTO> prescripciones;
    private List<SesionEstudioDTO> sesionEstudios;
    private String observacion;
    private LocalDateTime fechaHoraCierre;
    private String documento;
    private BigDecimal temperaturaCorporal;
    private BigDecimal pesoCorporal;
    private int frecuenciaCardiaca;
    private int presionArterial;
    private String diagnostico;
}

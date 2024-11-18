package com.ERP.ERP.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FichaAtencionDTO {
    private String horafechaSolicitada;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraCierre;
    private String estado;
    private Boolean asistencia = false;
    private PacienteDTO paciente;
    private MedicoDTO medico;
    private Boolean cancelada = false;
}

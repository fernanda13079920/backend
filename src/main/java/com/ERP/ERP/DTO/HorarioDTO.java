package com.ERP.ERP.DTO;

import lombok.Data;

@Data
public class HorarioDTO {
    private Integer diaSemana;
    private String horaInicio;
    private String horaFinal;
    private Integer duracionConsulta;
    private Boolean estado;
    private MedicoDTO medico;
}

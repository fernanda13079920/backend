package com.ERP.ERP.DTO;

import lombok.Data;

@Data
public class MedicoDTO {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Boolean estado;
    private String email;
    private UsuarioDTO usuario;
    private EspecialidadDTO especialidad;
}

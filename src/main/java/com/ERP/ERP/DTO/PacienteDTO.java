package com.ERP.ERP.DTO;

import lombok.Data;

@Data
public class PacienteDTO {
    private String ci;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String genero;
    private String direccion;
    private String telefono;
    private String email;
    private Boolean estado;
    private UsuarioDTO usuario;
}

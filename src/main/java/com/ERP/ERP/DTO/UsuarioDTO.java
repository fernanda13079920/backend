package com.ERP.ERP.DTO;

import com.ERP.ERP.model.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String correo;
    private String password;
    private Rol rol;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}

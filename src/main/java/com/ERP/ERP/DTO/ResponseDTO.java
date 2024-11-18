package com.ERP.ERP.DTO;

import com.ERP.ERP.model.Sesion;
import lombok.Data;

@Data
public class ResponseDTO {
    private Sesion sesion;
    private String errorMessage;

    // Getters and setters
    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}

package com.ERP.ERP.Controller;

import com.ERP.ERP.DTO.FichaAtencionDTO;
import com.ERP.ERP.model.FichaAtencion;
import com.ERP.ERP.service.FichaAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class FichaAtencionController {

    @Autowired
    private FichaAtencionService fichaAtencionService;
/*
    @GetMapping
    public ResponseEntity<List<FichaAtencion>> getAllConsultas() {
        List<FichaAtencion> consultas = FichaAtencionService.getAllPaciente();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }
*/
    @PostMapping
    public ResponseEntity<FichaAtencion> createFichaAtencion(@RequestBody FichaAtencionDTO fichaAtencionDTO) {
        try {
            FichaAtencion nuevaFichaAtencion = fichaAtencionService.createfichaAtencion(FichaAtencionDTO);
            return new ResponseEntity<>(nuevaFichaAtencion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

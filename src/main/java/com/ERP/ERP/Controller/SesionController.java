package com.ERP.ERP.Controller;

import com.ERP.ERP.DTO.SesionCrearDTO;
import com.ERP.ERP.DTO.SesionEditarDTO;
import com.ERP.ERP.model.Sesion;
import com.ERP.ERP.repository.SesionRepository;
import com.ERP.ERP.service.SesionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/sesion")
public class SesionController {

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private SesionService sesionService;

   // @Autowired
   // private PrescripcionService prescripcionService;

    @PutMapping("/{sesionId}")
        public ResponseEntity<Sesion> editarSesion(@PathVariable Long sesionId, @RequestBody SesionEditarDTO sesionEditarDTO) {
            try {
                Sesion sesion = sesionService.editarSesionCompleta(sesionId, sesionEditarDTO);
                return new ResponseEntity<>(sesion, HttpStatus.OK);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sesión no encontrada", e);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al editar la sesión", e);
            }
        }

    /*
        @PostMapping("/guardar/{fichaAtencionId}")
    public ResponseEntity<Sesion> guardarSesionCompleta(@PathVariable Long fichaAtencionId, @RequestBody SesionCrearDTO sesionCrearDTO) {
        try {
            Sesion sesion = sesionService.guardarSesionCompleta(fichaAtencionId, sesionCrearDTO);
            return new ResponseEntity<>(sesion, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ficha de atención no encontrada", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la sesión", e);
        }
    }

     */


    @PostMapping("/guardar/{fichaAtencionId}")
    public ResponseEntity<Sesion> guardarSesionCompleta(@PathVariable Long fichaAtencionId, @RequestBody SesionCrearDTO sesionCrearDTO) {
        try {
            Sesion sesion = sesionService.guardarSesionCompleta(fichaAtencionId, sesionCrearDTO);
            return new ResponseEntity<>(sesion, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{sesionId}")
    public ResponseEntity<Sesion> getSesionById(@PathVariable Long sesionId) {
        try {
            Sesion sesion = sesionService.getSesionById(sesionId);
            return new ResponseEntity<>(sesion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
/*
    @DeleteMapping("/{sesionId}/prescripciones")
    public ResponseEntity<Void> deletePrescriptionsBySesionId(@PathVariable Long sesionId) {
        try {
            deletePrescriptionsBySesionId( sesionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

*/

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Sesion>> getAllSessionsByPatientId(@PathVariable Long patientId) {
        List<Sesion> sessions = sesionService.getAllSessionsByPatientId(patientId);
        return ResponseEntity.ok(sessions);
    }


}

package com.ERP.ERP.Controller;

import com.ERP.ERP.model.Medico;
import com.ERP.ERP.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    // Crear un nuevo médico
    @PreAuthorize("hasAnyRole('Administrador')")
    @PostMapping("/crear")
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        try {
            Medico nuevoMedico = medicoService.crearMedico(medico);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMedico);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Listar todos los médicos
    @PreAuthorize("hasAnyRole('Administrador')")
    @GetMapping("/listar")
    public ResponseEntity<List<Medico>> listarMedicos() {
        return ResponseEntity.ok(medicoService.findAll());
    }
}

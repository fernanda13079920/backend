package com.ERP.ERP.Controller;


import com.ERP.ERP.DTO.EspecialidadDTO;
import com.ERP.ERP.model.Especialidad;
import com.ERP.ERP.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Endpoint para listar todas las especialidades
    @PreAuthorize("hasAnyRole('Administrador','Medico','Paciente')")
    @GetMapping("/listar")
    public ResponseEntity<List<Especialidad>> getAllEspecialidades() {
        return especialidadService.findAll();
    }

    // Endpoint para guardar una nueva especialidad
    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public ResponseEntity<Especialidad> createEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.save(especialidadDTO);
    }

    // Endpoint para modificar una especialidad existente
    @PreAuthorize("hasRole('Administrador')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Especialidad> updateEspecialidad(@PathVariable Long id, @RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.update(id, especialidadDTO);
    }

    // Endpoint para eliminar una especialidad
    @PreAuthorize("hasRole('Administrador')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.delete(especialidadDTO);
    }
    @PreAuthorize("hasAnyRole('Administrador','Medico','Paciente')")
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> getEspecialidadById(@PathVariable Long id) {
        try {
            Especialidad especialidad = especialidadService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
            return ResponseEntity.ok(especialidad);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
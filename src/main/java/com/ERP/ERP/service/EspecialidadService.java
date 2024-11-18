package com.ERP.ERP.service;

import com.ERP.ERP.DTO.EspecialidadDTO;
import com.ERP.ERP.model.Especialidad;
import com.ERP.ERP.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    // Listar todas las especialidades
    public ResponseEntity<List<Especialidad>> findAll() {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        return new ResponseEntity<>(especialidades, HttpStatus.OK);
    }

    // Guardar una nueva especialidad
    public ResponseEntity<Especialidad> save(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setDescripcion(especialidadDTO.getDescripcion());

        Especialidad savedEspecialidad = especialidadRepository.save(especialidad);
        return new ResponseEntity<>(savedEspecialidad, HttpStatus.CREATED);
    }

    // Modificar una especialidad existente
    public ResponseEntity<Especialidad> update(Long id, EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(id);

        if (optionalEspecialidad.isPresent()) {
            Especialidad especialidad = optionalEspecialidad.get();
            especialidad.setNombre(especialidadDTO.getNombre());
            especialidad.setDescripcion(especialidadDTO.getDescripcion()); // Asegúrate de que este campo esté correcto en la entidad
            Especialidad updatedEspecialidad = especialidadRepository.save(especialidad);
            return new ResponseEntity<>(updatedEspecialidad, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una especialidad
    public ResponseEntity<Void> delete(EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findByNombre(especialidadDTO.getNombre());

        if (optionalEspecialidad.isPresent()) {
            especialidadRepository.deleteById(optionalEspecialidad.get().getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public Optional<Especialidad> findById(Long id) {
        return especialidadRepository.findById(id);
    }

}


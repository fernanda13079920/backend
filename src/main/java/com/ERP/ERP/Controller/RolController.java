package com.ERP.ERP.Controller;

import com.ERP.ERP.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ERP.ERP.service.RolService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rol")
public class RolController {
    @Autowired
    private RolService rolService;

    @PreAuthorize("hasRole('Administrador')")
    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> read() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @PreAuthorize("hasRole('Administrador')")
    @PostMapping(path = "/guardar")
    public ResponseEntity<Rol> crear(@RequestBody Rol c){
        try {
            Rol nuevo = rolService.save(c);
            return ResponseEntity.created(new URI("/usuario/guardar/"+nuevo.getId())).body(nuevo);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


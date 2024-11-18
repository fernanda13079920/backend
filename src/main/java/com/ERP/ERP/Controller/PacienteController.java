package com.ERP.ERP.Controller;

import com.ERP.ERP.model.Paciente;
import com.ERP.ERP.service.PacienteService;
import com.ERP.ERP.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/cargar-excel")
    public ResponseEntity<String> cargarPacientesDesdeExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Paciente> pacientesExcel = ExcelHelper.excelToPacientes(file.getInputStream());
            pacienteService.registrarPacientesDesdeExcel(pacientesExcel);
            return ResponseEntity.ok("Pacientes procesados correctamente. Credenciales enviadas por correo.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar el archivo Excel.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado.");
        }
    }
}

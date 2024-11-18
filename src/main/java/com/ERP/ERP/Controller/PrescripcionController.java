package com.ERP.ERP.Controller;

import com.ERP.ERP.service.PrescripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ERP.ERP.service.SesionService;

@RestController
@RequestMapping("/prescripcion")
public class PrescripcionController {

    @Autowired
    private PrescripcionService prescripcionService;
    @Autowired
    private SesionService SesionService;

  /*
    @DeleteMapping("/sesion/{sesionId}")
    public ResponseEntity<Void> deletePrescriptionsBySesionId(@PathVariable Long sesionId) {
        try {
            prescripcionService.deletePrescriptionsBySesionId(sesionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */

    @DeleteMapping("/{sesionId}")
    public ResponseEntity<?> deletePrescriptionsBySesionId(@PathVariable Long sesionId) {
        try {
            System.out.println("Attempting to delete prescriptions for session ID: " + sesionId);
            prescripcionService.deletePrescriptionsBySesion_Id(sesionId);
            System.out.println("Successfully deleted prescriptions for session ID: " + sesionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            System.err.println("RuntimeException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }



    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Controller is working");
    }


}